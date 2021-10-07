package it.ivirus.telegramlogin.data;

import it.ivirus.telegramlogin.util.TelegramPlayer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PlayerData {
    private final Map<UUID, TelegramPlayer> playerInLogin = new HashMap<>();
    private final Set<UUID> playerWaitingForChatid = new HashSet<>();
    private final Set<UUID> bungeePendingPlayers = new HashSet<>();

    @Getter(lazy = true)
    private static final PlayerData instance = new PlayerData();
}
