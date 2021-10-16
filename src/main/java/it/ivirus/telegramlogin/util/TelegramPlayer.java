package it.ivirus.telegramlogin.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
public class TelegramPlayer {
    private final String playerUUID;
    private final String chatID;
    @Setter
    private boolean locked;
    private final Date registrationDate;
}
