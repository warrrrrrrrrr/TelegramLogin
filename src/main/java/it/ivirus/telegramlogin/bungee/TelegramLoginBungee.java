package it.ivirus.telegramlogin.bungee;

import com.google.common.io.ByteStreams;
import it.ivirus.telegramlogin.bungee.listener.MessageListener;
import it.ivirus.telegramlogin.bungee.listener.PlayerListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class TelegramLoginBungee extends Plugin {
    @Getter private static TelegramLoginBungee instance;

    private File fileConfig;
    private Configuration configuration;

    @Override
    public void onEnable() {
        instance = this;

        new PlayerListener();
        new MessageListener();
    }

    @Override
    public void onDisable() {
        //
    }

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
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFileConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
