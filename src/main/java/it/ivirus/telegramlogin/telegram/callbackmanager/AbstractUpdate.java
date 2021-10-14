package it.ivirus.telegramlogin.telegram.callbackmanager;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.data.PlayerData;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractUpdate {
    protected final PlayerData playerData = PlayerData.getInstance();
    protected final TelegramLogin plugin = TelegramLogin.getInstance();

    public abstract void onUpdateCall(TelegramBot bot, Update update, String[] args);
}
