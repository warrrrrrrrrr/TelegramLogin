package it.ivirus.telegramlogin.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import it.ivirus.telegramlogin.velocity.listener.MessageListener;
import it.ivirus.telegramlogin.velocity.listener.PlayerListener;
import lombok.Getter;

@Plugin(id = "telegramlogin", name = "TelegramLogin", version = "1.0.3",
        url = "https://discord.io/hoxija", authors = {"iVirus"})
public class TelegramLoginVelocity {
    @Getter
    private final ProxyServer server;

    @Inject
    public TelegramLoginVelocity(ProxyServer server) {
        this.server = server;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new PlayerListener());
        server.getEventManager().register(this, new MessageListener());
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("hxj", "telegramlogin"));
    }

}
