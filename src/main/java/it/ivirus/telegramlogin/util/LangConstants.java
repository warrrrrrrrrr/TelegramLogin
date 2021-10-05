package it.ivirus.telegramlogin.util;

import it.ivirus.telegramlogin.TelegramLogin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public enum LangConstants {

    PREFIX("prefix"),
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
        for (String s : lang.getStringList(path)) {
            stringBuilder.append(s + "\n");
        }
        return stringBuilder.toString().replaceAll("%prefix%", getPrefix());
    }

    public String getFormattedString() {
        return Util.color(getString());
    }

}
