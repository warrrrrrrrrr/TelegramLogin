package it.ivirus.telegramlogin.listeners;

import it.ivirus.telegramlogin.TelegramLogin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {
    private final TelegramLogin plugin;

    public LoginListener(TelegramLogin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event){

    }
}
