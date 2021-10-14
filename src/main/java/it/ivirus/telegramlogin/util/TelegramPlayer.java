package it.ivirus.telegramlogin.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@RequiredArgsConstructor
@Getter
public class TelegramPlayer {
    private final String playerUUID;
    private final String chatID;
    private final boolean locked;
    private final Date registrationDate;
}
