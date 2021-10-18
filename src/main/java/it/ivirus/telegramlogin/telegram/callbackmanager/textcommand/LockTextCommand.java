package it.ivirus.telegramlogin.telegram.callbackmanager.textcommand;

import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.telegram.callbackmanager.AbstractUpdate;
import it.ivirus.telegramlogin.util.LangConstants;
import it.ivirus.telegramlogin.util.MessageFactory;
import it.ivirus.telegramlogin.util.PluginMessageAction;
import it.ivirus.telegramlogin.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.UUID;

public class LockTextCommand extends AbstractUpdate {
    @Override
    public void onUpdateCall(TelegramBot bot, Update update, String[] args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        plugin.getSql().getTelegramPlayerByChatId(chatId).whenComplete((telegramPlayer, throwable) -> {
            if (throwable != null)
                throwable.printStackTrace();
        }).thenAccept(telegramPlayer -> {
            try {
                if (telegramPlayer == null) {
                    bot.execute(MessageFactory.simpleMessage(chatId, LangConstants.TG_CHATID_NOT_LINKED.getString()));
                } else {
                    plugin.getSql().setLockPlayerByChatId(chatId, true);
                    if (playerData.getPlayerCache().containsKey(UUID.fromString(telegramPlayer.getPlayerUUID()))){
                        playerData.getPlayerCache().get(UUID.fromString(telegramPlayer.getPlayerUUID())).setLocked(true);
                    }
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        Player player = Bukkit.getPlayer(UUID.fromString(telegramPlayer.getPlayerUUID()));
                        if (player != null){
                            if (playerData.getPlayerInLogin().containsKey(player.getUniqueId())){
                                playerData.getPlayerInLogin().remove(player.getUniqueId());
                                Util.sendPluginMessage(player, PluginMessageAction.REMOVE);
                                player.kickPlayer(LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString());
                            }
                        }
                    },1);
                    bot.execute(MessageFactory.simpleMessage(chatId, LangConstants.TG_LOCKED_MESSAGE_BY_COMMAND.getString()));
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }
}
