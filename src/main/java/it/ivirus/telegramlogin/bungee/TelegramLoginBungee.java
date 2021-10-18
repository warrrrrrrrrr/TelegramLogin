package it.ivirus.telegramlogin.bungee;

import it.ivirus.telegramlogin.bungee.listener.MessageListener;
import it.ivirus.telegramlogin.bungee.listener.PlayerListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class TelegramLoginBungee extends Plugin {
    @Getter
    private static TelegramLoginBungee instance;

    @Override
    public void onEnable() {
        instance = this;
        this.loadListeners(new PlayerListener(), new MessageListener());
        this.getProxy().registerChannel("hxj:telegramlogin");
    }

    @Override
    public void onDisable() {
        //
    }

    private void loadListeners(Listener... listeners) {
        for (Listener l : listeners) {
            this.getProxy().getPluginManager().registerListener(this, l);
        }
    }

}
