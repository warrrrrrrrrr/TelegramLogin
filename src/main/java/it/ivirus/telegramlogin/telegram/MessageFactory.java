package it.ivirus.telegramlogin.telegram;

import it.ivirus.telegramlogin.util.LangConstants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageFactory {

    public static SendMessage addConfirm(String playerUUID, String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(LangConstants.TG_ADD_MESSAGE.getString());
        sendMessage.setReplyMarkup(KeyboardFactory.addConfirmButtons(playerUUID, chatId));
        return sendMessage;
    }

    public static SendMessage loginRequest(String playerUUID, String chatId, String ipAddress){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(LangConstants.TG_LOGIN_MESSAGE.getString().replaceAll("%player_ip%", ipAddress));
        sendMessage.setReplyMarkup(KeyboardFactory.addConfirmButtons(playerUUID, chatId));
        return sendMessage;
    }

    public static SendMessage chatIdConfirmed(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(LangConstants.TG_CHATID_CONFIRMED.getString());
        return sendMessage;
    }

    public static SendMessage playerOffline(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(LangConstants.TG_PLAYER_OFFLINE.getString());
        return sendMessage;
    }
}
