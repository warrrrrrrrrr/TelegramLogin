package it.ivirus.telegramlogin.bungee.listener;

import it.ivirus.telegramlogin.bungee.TelegramLoginBungee;
import it.ivirus.telegramlogin.data.PlayerData;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PlayerListener implements Listener {

    public PlayerListener() {
        TelegramLoginBungee.getInstance().getProxy().getPluginManager().registerListener(TelegramLoginBungee.getInstance(), this);
    }

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        if (TelegramLoginBungee.getInstance().getConfiguration().getBoolean("bungee"))
            PlayerData.getInstance().getBungeePendingPlayers().add(playerUUID);
    }

    @EventHandler
    public void onCommand(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (event.isProxyCommand() && TelegramLoginBungee.getInstance().getConfiguration().getBoolean("bungee")) {
            if (PlayerData.getInstance().getBungeePendingPlayers().contains(player.getUniqueId())) {
                // TODO
                event.setCancelled(true);
            }
        }
    }

}
