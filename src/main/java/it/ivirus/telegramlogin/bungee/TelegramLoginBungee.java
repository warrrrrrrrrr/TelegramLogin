package it.ivirus.telegramlogin.bungee;

import com.google.common.io.ByteStreams;
import it.ivirus.telegramlogin.bungee.listener.MessageListener;
import it.ivirus.telegramlogin.bungee.listener.PlayerListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

@Getter
public class TelegramLoginBungee extends Plugin {
    @Getter private static TelegramLoginBungee instance;

    private File fileConfig;
    private Configuration config;

    @Override
    public void onEnable() {
        instance = this;
        this.createConfig();
        this.loadListeners(new PlayerListener(), new MessageListener());
    }

    @Override
    public void onDisable() {
        //
    }

    @SuppressWarnings("UnstableApiUsage")
    private void createConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        fileConfig = new File(getDataFolder(), "config.yml");
        if (!fileConfig.exists()) {
            try {
                fileConfig.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(fileConfig)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error on creating config file", e);
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFileConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadListeners(Listener... listeners){
        for (Listener l : listeners){
            this.getProxy().getPluginManager().registerListener(this, l);
        }
    }

}
