package it.ivirus.telegramlogin.util;

import it.ivirus.telegramlogin.TelegramLogin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public enum LangConstants {

    PREFIX("prefix"),
    TG_ADD_MESSAGE("info.telegram.addMessage"),
    TG_LOGIN_MESSAGE("info.telegram.loginMessage"),
    TG_LOCKED_MESSAGE("info.telegram.lockedMessage"),
    TG_ABORT_BUTTON_TEXT("info.telegram.abortButton"),
    TG_CONFIRM_BUTTON_TEXT("info.telegram.confirmButton"),
    TG_LOCK_BUTTON_TEXT("info.telegram.lockButton"),
    TG_UNLOCK_BUTTON_TEXT("info.telegram.unlockButton"),
    TG_EXECUTED_MESSAGE("info.telegram.executed"),
    TG_ABORTED_MESSAGE("info.telegram.aborted"),
    TG_CHATID_CONFIRMED("info.telegram.chatIdConfirmed"),
    TG_PLAYER_OFFLINE("errors.telegram.playerOffline"),
    ACCOUNT_LINKED("info.accountLinked"),
    ADD_CHATID("info.addChatId"),
    WAIT_FOR_CONFIRM("info.waitForConfirm"),
    WAIT_FOR_LOGIN_CONFIRM("info.waitForLoginConfirm"),
    INVALID_VALUE("errors.invalidValue"),
    OPERATION_ABORTED("info.operationAborted"),
    KICK_LOG_AGAIN("info.kick_logagain"),
    CHATID_ALREADY_USED("errors.chatIdAlreadyUsed"),
    NOPERMISSION("errors.noPermission");


    private final TelegramLogin plugin = TelegramLogin.getInstance();
    private final FileConfiguration lang = plugin.getLangConfig();
    @Getter
    private final String path;

    LangConstants(String path) {
        this.path = path;
    }

    private String getPrefix() {
        return lang.getString(PREFIX.getPath());
    }

    public String getString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (lang.isList(path)){
            for (String s : lang.getStringList(path)) {
                stringBuilder.append(s + "\n");
            }
        } else {
            return lang.getString(path).replaceAll("%prefix%", getPrefix());
        }
        return stringBuilder.toString().replaceAll("%prefix%", getPrefix());
    }

    public String getFormattedString() {
        return Util.color(getString());
    }

}
