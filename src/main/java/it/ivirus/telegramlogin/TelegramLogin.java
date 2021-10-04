package it.ivirus.telegramlogin;

import it.ivirus.telegramlogin.database.SQLite;
import it.ivirus.telegramlogin.database.SqlManager;
import it.ivirus.telegramlogin.database.remote.MySQL;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.sql.SQLException;

@Getter
public class TelegramLogin extends JavaPlugin {
    @Getter
    private static TelegramLogin instance;
    private Thread botThread;
    private TelegramBotsApi botsApi;
    private SqlManager sql;
    private File langFile;
    private FileConfiguration langConfig;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.createLangFile("en_US", "it_IT");
        this.loadLangConfig();
        this.setupDb();
        startBot();
    }

    private void setupDb() {
        String database = getConfig().getString("MySQL.Database");
        String username = getConfig().getString("MySQL.Username");
        String password = getConfig().getString("MySQL.Password");
        String host = getConfig().getString("MySQL.Host");
        int port = getConfig().getInt("MySQL.Port");
        boolean ssl = getConfig().getBoolean("MySQL.SSL");
        sql = getConfig().getBoolean("MySQL.Enable") ? new MySQL(this, database, username, password, host, port, ssl) : new SQLite(this);
        sql.setup();
        try {
            sql.createTables();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void startBot(){
        botThread = new Thread(() -> {
            try {
                botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new TelegramBot(this));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
        botThread.start();
    }

    private void createLangFile(String... names) {
        for (String name : names) {
            if (!new File(getDataFolder(), "languages" + File.separator + name + ".yml").exists()) {
                saveResource("languages" + File.separator + name + ".yml", false);
            }
        }
    }

    private void loadLangConfig() {
        langFile = new File(getDataFolder(), "languages" + File.separator + getConfig().getString("plugin.language") + ".yml");
        if (!langFile.exists()) {
            langFile = new File(getDataFolder(), "languages" + File.separator + "en_US.yml");
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }
}
