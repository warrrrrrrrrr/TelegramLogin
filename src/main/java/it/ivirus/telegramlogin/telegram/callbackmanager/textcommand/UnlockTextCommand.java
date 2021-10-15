package it.ivirus.telegramlogin.telegram.callbackmanager.textcommand;

import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.telegram.callbackmanager.AbstractUpdate;
import it.ivirus.telegramlogin.util.LangConstants;
import it.ivirus.telegramlogin.util.MessageFactory;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class UnlockTextCommand extends AbstractUpdate {
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
                    plugin.getSql().setLockPlayerByChatId(chatId, false);
                    bot.execute(MessageFactory.simpleMessage(chatId, LangConstants.TG_UNLOCKED_MESSAGE.getString()));
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }
}
