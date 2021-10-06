package it.ivirus.telegramlogin.telegram;

import it.ivirus.telegramlogin.util.LangConstants;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {
    public static ReplyKeyboard addConfirmButtons(String playerUUID, String idChat) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton confirmButton = new InlineKeyboardButton();
        System.out.println(LangConstants.TG_CONFIRM_BUTTON_TEXT.getString());
        confirmButton.setText(LangConstants.TG_CONFIRM_BUTTON_TEXT.getString());
        confirmButton.setCallbackData("/addconfirm " + playerUUID + " " + idChat);
        rowInline.add(confirmButton);
        InlineKeyboardButton abortButton = new InlineKeyboardButton();
        abortButton.setText(LangConstants.TG_ABORT_BUTTON_TEXT.getString());
        abortButton.setCallbackData("/addabort " + playerUUID + " " + idChat);
        rowInline.add(abortButton);
        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }
}
