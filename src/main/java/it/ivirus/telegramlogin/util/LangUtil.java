package it.ivirus.telegramlogin.util;

import it.ivirus.telegramlogin.TelegramLogin;

import java.util.List;

public class LangUtil {
    public static String color(String string) {
        return HexResolver.parseHexString(string);
    }

    public static String PREFIX() {
        return color(TelegramLogin.getInstance().getConfig().getString("plugin.prefix"));
    }

    public static String getMessage(String path) {
        return color(TelegramLogin.getInstance().getLangConfig().getString(path));
    }

    public static List<String> getStringList(String path) {
        return TelegramLogin.getInstance().getLangConfig().getStringList(path);
    }
}
