package it.ivirus.telegramlogin.telegram.callbackmanager;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.telegram.callbackmanager.callbackcommand.AbortCallbackQuery;
import it.ivirus.telegramlogin.telegram.callbackmanager.callbackcommand.AddConfirmCallbackQuery;
import it.ivirus.telegramlogin.telegram.callbackmanager.callbackcommand.LockCallbackQuery;
import it.ivirus.telegramlogin.telegram.callbackmanager.callbackcommand.LoginConfirmCallbackQuery;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class TextCommandHandler {
    private final TelegramLogin plugin = TelegramLogin.getInstance();

    private TextCommandHandler() {
        registerCommand("/help", new AddConfirmCallbackQuery());
        registerCommand("/start", new AbortCallbackQuery());
        registerCommand("/chatid", new LoginConfirmCallbackQuery());
        registerCommand("/lock", new LockCallbackQuery());
        registerCommand("/unlock", new LockCallbackQuery());
    }

    @Getter(lazy = true)
    private static final TextCommandHandler instance = new TextCommandHandler();

    private final Map<String, AbstractUpdate> commands = new HashMap<>();

    public void run(Update update) {
        String[] args = update.getCallbackQuery().getData().split(" ");
        if (!commands.containsKey(args[0])) return;

        commands.get(args[0]).onUpdateCall(plugin.getBot(), update, args);
    }

    private void registerCommand(String cmd, AbstractUpdate abstractUpdate) {
        commands.put(cmd, abstractUpdate);
    }
}
