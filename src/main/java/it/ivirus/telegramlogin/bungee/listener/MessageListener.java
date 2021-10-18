package it.ivirus.telegramlogin.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import it.ivirus.telegramlogin.bungee.TelegramLoginBungee;
import it.ivirus.telegramlogin.data.PlayerData;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Locale;
import java.util.UUID;

public class MessageListener implements Listener {

    public MessageListener() {
        TelegramLoginBungee.getInstance().getProxy().registerChannel("hxj:telegramlogin");
    }

    @EventHandler
    @SuppressWarnings("UnstableApiUsage")
    public void onMessageReceived(PluginMessageEvent event) {
        if (!TelegramLoginBungee.getInstance().getConfig().getBoolean("bungee")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
        String subChannel = input.readLine();
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
            default:
                return;
        }
    }

}
