package it.ivirus.telegramlogin.telegram;

import it.ivirus.telegramlogin.util.LangConstants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageFactory {

    public SendMessage addConfirm(String playerName, String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(LangConstants.TG_ADD_MESSAGE.getString());
        sendMessage.setReplyMarkup(KeyboardFactory.addConfirmButtons(playerName, chatId));
        return sendMessage;
    }
}
