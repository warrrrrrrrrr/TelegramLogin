package it.ivirus.telegramlogin.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import it.ivirus.telegramlogin.data.PlayerData;

public class PlayerListener {

    @Subscribe
    public void onCommand(CommandExecuteEvent event) {
        if (!(event.getCommandSource() instanceof Player)) return;
        Player player = (Player) event.getCommandSource();

        if (PlayerData.getInstance().getBungeePendingPlayers().contains(player.getUniqueId())) {
            event.setResult(CommandExecuteEvent.CommandResult.denied());
        }

    }

    @Subscribe
    public void onPlayerQuit(DisconnectEvent event) {
        PlayerData.getInstance().getBungeePendingPlayers().remove(event.getPlayer().getUniqueId());
    }

}
