package it.ivirus.telegramlogin.util;

import it.ivirus.telegramlogin.TelegramLogin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public enum LangConstants {

    PREFIX("prefix"),
    TG_ADD_MESSAGE("info.telegram.addMessage"),
    TG_LOGIN_MESSAGE("info.telegram.loginMessage"),
    TG_LOCKED_MESSAGE("info.telegram.lockedMessage"),
    TG_LOCKED_MESSAGE_BY_COMMAND("info.telegram.lockedMessageByCommand"),
    TG_CHATID_NOT_LINKED("errors.telegram.chatIdNotLinked"),
    TG_ACCOUNTID_NOT_LINKED("errors.telegram.accountIdNotLinked"),
    TG_UNLOCKED_MESSAGE("info.telegram.unlockedMessage"),
    TG_ABORT_BUTTON_TEXT("info.telegram.abortButton"),
    TG_CONFIRM_BUTTON_TEXT("info.telegram.confirmButton"),
    TG_LOGIN_EXECUTED("info.telegram.loginExecuted"),
    TG_LOCK_BUTTON_TEXT("info.telegram.lockButton"),
    TG_UNLOCK_BUTTON_TEXT("info.telegram.unlockButton"),
    TG_EXECUTED_MESSAGE("info.telegram.executed"),
    TG_ABORTED_MESSAGE("info.telegram.aborted"),
    TG_CHATID_CONFIRMED("info.telegram.chatIdConfirmed"),
    TG_PLAYER_OFFLINE("errors.telegram.playerOffline"),
    TG_HELP_MESSAGE("info.telegram.helpMessage"),
    TG_START_MESSAGE("info.telegram.startMessage"),
    TG_CHATID_MESSAGE("info.telegram.chatIdMessage"),
    TG_ACCOUNT_LIST("info.telegram.accountList.telegramMessage"),
    TG_ACCOUNT_LIST_SINTAX("info.telegram.accountList.telegramSintax"),
    TG_ACCOUNT_LIST_LOCKED("info.telegram.accountList.locked"),
    TG_ACCOUNT_LIST_UNLOCKED("info.telegram.accountList.unlocked"),
    MC_ACCOUNT_LIST("info.accountList.minecraftMessage"),
    MC_ACCOUNT_LIST_SINTAX("info.accountList.minecraftSintax"),
    MC_ACCOUNT_LIST_LOCKED("info.accountList.locked"),
    MC_ACCOUNT_LIST_UNLOCKED("info.accountList.unlocked"),
    ACCOUNT_LINKED("info.accountLinked"),
    ADD_CHATID("info.addChatId"),
    CHATID_CHANGED("info.chatIdChanged"),
    TARGET_REMOVED("info.targetRemoved"),
    CANNOT_REMOVE("errors.2FA.cannotRemove"),
    TWOFA_DISABLED("errors.2FA.disabled"),
    SENDER_ALREADY_2FA("errors.2FA.playerAlready2FA"),
    SENDER_WITHOUT_TELEGRAMLOGIN("errors.2FA.playerWithoutTelegramLogin"),
    WAIT_FOR_CONFIRM("info.waitForConfirm"),
    WAIT_FOR_LOGIN_CONFIRM("info.waitForLoginConfirm"),
    INVALID_VALUE("errors.invalidValue"),
    TG_INVALID_VALUE("errors.telegram.invalidValue"),
    OPERATION_ABORTED("info.operationAborted"),
    ABORT_2FA("info.2FA.abort2FA"),
    ACCOUNT_DISCONNECTED("info.2FA.accountDisconnected"),
    KICK_LOG_AGAIN("info.kick_logagain"),
    KICK_ACCOUNT_LOCKED("info.kick_accountLocked"),
    LOGIN_EXECUTED("info.loginExecuted"),
    HELP_PLAYER("info.help.player"),
    HELP_ADMIN("info.help.admin"),
    RELOAD_EXECUTED("info.reloadExecuted"),
    ONLY_PLAYER("errors.onlyPlayer"),
    CHANGE_CHATID_USAGE("errors.changeChatIdUsage"),
    REMOVE_USAGE("errors.removeUsage"),
    TARGET_OFFLINE("errors.targetOffline"),
    TARGET_WITHOUT_TELEGRAM_LOGIN("errors.targetWithoutTelegramLogin"),
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
