package it.ivirus.telegramlogin.telegram.callbackmanager.textcommand;

import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.telegram.callbackmanager.AbstractUpdate;
import it.ivirus.telegramlogin.util.LangConstants;
import it.ivirus.telegramlogin.util.MessageFactory;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ChatidTextCommand extends AbstractUpdate {
    @Override
    public void onUpdateCall(TelegramBot bot, Update update, String[] args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        try {
            bot.execute(MessageFactory.chatidMessage(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
