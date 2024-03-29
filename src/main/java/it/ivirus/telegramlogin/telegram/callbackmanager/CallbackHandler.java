package it.ivirus.telegramlogin.telegram.callbackmanager;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.telegram.callbackmanager.callbackcommand.*;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class CallbackHandler {

    private final TelegramLogin plugin = TelegramLogin.getInstance();

    private CallbackHandler() {
        registerCommand("/addconfirm", new AddConfirmCallbackQuery());
        registerCommand("/abort", new AbortCallbackQuery());
        registerCommand("/loginconfirm", new LoginConfirmCallbackQuery());
        registerCommand("/lock", new LockCallbackQuery());
        registerCommand("/unlock", new UnlockCallbackQuery());
    }

    @Getter(lazy = true)
    private static final CallbackHandler instance = new CallbackHandler();

    private final Map<String, AbstractUpdate> commands = new HashMap<>();

    public void run(Update update) {
        String[] args = update.getCallbackQuery().getData().split(" ");
        if (!commands.containsKey(args[0])) {
            return;
        }

        commands.get(args[0]).onUpdateCall(plugin.getBot(), update, args);
    }

    private void registerCommand(String cmd, AbstractUpdate abstractUpdate) {
        commands.put(cmd, abstractUpdate);
    }
}
