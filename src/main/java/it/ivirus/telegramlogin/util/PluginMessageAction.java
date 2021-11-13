package it.ivirus.telegramlogin.util;

import lombok.Getter;

public enum PluginMessageAction {
    ADD ("add"),
    ADD2FA("add2fa"),
    REMOVE ("remove");

    @Getter
    private final String type;
    PluginMessageAction(String type){
        this.type = type;
    }
}
