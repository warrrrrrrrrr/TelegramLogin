package it.ivirus.telegramlogin.telegram.callbackmanager.callbackcommand;

import it.ivirus.telegramlogin.telegram.KeyboardFactory;
import it.ivirus.telegramlogin.telegram.MessageFactory;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.telegram.callbackmanager.CallbackCommand;
import it.ivirus.telegramlogin.util.LangConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.UUID;

public class LockCallbackQuery extends CallbackCommand {
    @Override
    public void onUpdateCall(TelegramBot bot, Update update, String[] args) {
        String playerUUID = args[1];
        UUID uuid = UUID.fromString(playerUUID);
        Player player = Bukkit.getPlayer(uuid);
        String chatId = args[2];
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        try {
            bot.execute(deleteMessage);
            plugin.getSql().setLockPlayer(playerUUID, true);
            if (player != null) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.kickPlayer(LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString()),1);
            }
            bot.execute(MessageFactory.simpleMessageButtons(chatId, LangConstants.TG_LOCKED_MESSAGE.getString(), KeyboardFactory.unlockButton(playerUUID, chatId)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
