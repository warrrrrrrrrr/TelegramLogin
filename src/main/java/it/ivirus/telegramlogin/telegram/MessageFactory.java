package it.ivirus.telegramlogin.telegram;

import it.ivirus.telegramlogin.util.LangConstants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class MessageFactory {

    public static SendMessage simpleMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public static SendMessage simpleMessageButtons(String chatId, String text, ReplyKeyboard buttons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(buttons);
        return sendMessage;
    }

    public static SendMessage loginRequest(String playerUUID, String chatId, String ipAddress) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(LangConstants.TG_LOGIN_MESSAGE.getString().replaceAll("%player_ip%", ipAddress));
        sendMessage.setReplyMarkup(KeyboardFactory.loginRequestButtons(playerUUID, chatId));
        return sendMessage;
    }

}
