package it.ivirus.telegramlogin.velocity.listener;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import it.ivirus.telegramlogin.data.PlayerData;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PlayerListener {

    private final ProxyServer proxyServer;

    @Subscribe
    public void onCommand(CommandExecuteEvent event) {
        if (!(event.getCommandSource() instanceof Player)) return;
        Player player = (Player) event.getCommandSource();
        String[] commandSplitted = event.getCommand().split(" ");
        if (!proxyServer.getCommandManager().hasCommand(commandSplitted[0])) return;

        if (PlayerData.getInstance().getBungeePendingPlayers().contains(player.getUniqueId())) {
            event.setResult(CommandExecuteEvent.CommandResult.denied());
        }

    }

    @Subscribe
    public void onPlayerQuit(DisconnectEvent event) {
        PlayerData.getInstance().getBungeePendingPlayers().remove(event.getPlayer().getUniqueId());
    }

}
