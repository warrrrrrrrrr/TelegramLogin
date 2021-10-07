package it.ivirus.telegramlogin.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import it.ivirus.telegramlogin.bungee.TelegramLoginBungee;
import it.ivirus.telegramlogin.data.PlayerData;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class MessageListener implements Listener {

    public MessageListener() {
        TelegramLoginBungee.getInstance().getProxy().registerChannel("{channel-name}");
        TelegramLoginBungee.getInstance().getProxy().getPluginManager().registerListener(TelegramLoginBungee.getInstance(), this);
    }

    @EventHandler @SuppressWarnings("UnstableApiUsage")
    public void onMessageReceived(PluginMessageEvent event) {
        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());

        String subChannel = input.readUTF();
        if (!subChannel.equals("{sub-channel-name")) return;

        UUID playerUUID = UUID.fromString(input.readUTF());

        if (TelegramLoginBungee.getInstance().getConfiguration().getBoolean("bungee"))
            PlayerData.getInstance().getBungeePendingPlayers().remove(playerUUID);
    }

}
