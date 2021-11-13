package it.ivirus.telegramlogin.velocity.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import it.ivirus.telegramlogin.data.PlayerData;

import java.util.UUID;

public class MessageListener {

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    public void onMessageReceived(PluginMessageEvent event) {

        if (!(event.getIdentifier().getId().equalsIgnoreCase("hxj:telegramlogin"))
                || !(event.getSource() instanceof ServerConnection))
            return;

        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());

        String subChannel = input.readUTF();
        if (!subChannel.equals("cache")) return;

        String action = input.readUTF();
        UUID playerUUID = UUID.fromString(input.readUTF());
        switch (action.toLowerCase()) {
            case "add": {
                PlayerData.getInstance().getBungeePendingPlayers().add(playerUUID);
                break;
            }
            case "remove": {
                PlayerData.getInstance().getBungeePendingPlayers().remove(playerUUID);
                break;
            }
            case "add2fa": {
                PlayerData.getInstance().getPlayerWaitingForChatid().add(playerUUID);
                break;
            }
            default:
                return;
        }
    }

}
