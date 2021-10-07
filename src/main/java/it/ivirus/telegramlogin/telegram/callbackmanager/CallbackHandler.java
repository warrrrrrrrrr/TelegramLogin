package it.ivirus.telegramlogin.telegram.callbackmanager;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.telegram.callbackmanager.callbackcommand.AddAbortCallbackQuery;
import it.ivirus.telegramlogin.telegram.callbackmanager.callbackcommand.AddConfirmCallbackQuery;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class CallbackHandler {
    private final TelegramLogin plugin = TelegramLogin.getInstance();

    private CallbackHandler() {
        registerCommand("/addconfirm", new AddConfirmCallbackQuery());
        registerCommand("/addabort", new AddAbortCallbackQuery());
    }

    @Getter(lazy = true)
    private static final CallbackHandler instance = new CallbackHandler();

    private final Map<String, CallbackCommand> commands = new HashMap<>();

    public void run(Update update) {
        String[] args = update.getCallbackQuery().getData().split(" ");
        if (!commands.containsKey(args[0])) return;

        commands.get(args[0]).onUpdateCall(plugin.getBot(), update, args);
    }

    private void registerCommand(String cmd, CallbackCommand callbackCommand) {
        commands.put(cmd, callbackCommand);
    }
}
