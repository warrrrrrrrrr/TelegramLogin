package it.ivirus.telegramlogin.telegram.callbackmanager;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class CallbackCommand {

    public abstract void onUpdateCall(Update update, String[] args);
}
