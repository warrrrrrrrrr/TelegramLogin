package it.ivirus.telegramlogin.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerData {

    @Getter
    private final Set<UUID> playerInLogin = new HashSet<>();

    @Getter(lazy = true)
    private static final PlayerData instance = new PlayerData();
}
