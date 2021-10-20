package it.ivirus.telegramlogin;

import it.ivirus.telegramlogin.database.SQLite;
import it.ivirus.telegramlogin.database.SqlManager;
import it.ivirus.telegramlogin.database.remote.MySQL;
import it.ivirus.telegramlogin.spigot.listeners.PlayerListener;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.util.Secure;
import it.ivirus.telegramlogin.util.Util;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.Executor;

@Getter
public class TelegramLogin extends JavaPlugin {
    @Getter
    private static TelegramLogin instance;
    private Thread botThread;
    private TelegramBotsApi botsApi;
    private TelegramBot bot;
    private SqlManager sql;
    private File langFile;
    private FileConfiguration langConfig;
    private final Executor executor = runnable -> Bukkit.getScheduler().runTaskAsynchronously(this, runnable);

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.createLangFile("en_US", "it_IT");
        this.loadLangConfig();

        if(!new Secure(this, getConfig().getString("license"), "http://hoxija.it:8080/api/client", "qWrpfnSRhBgYCjhtUdymzu3pY7YpNcPuXruG5C7D").verify()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return;
        }

        this.setupDb();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "hxj:telegramlogin");
        this.startBot();
        getLogger().info("Plugin is ready!");
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

    @Override
    public void onDisable() {
        if (getConfig().getBoolean("MySQL.Enable")){
            MySQL mysql = (MySQL) sql;
            mysql.closePool();
        }
        botThread.stop();
    }

    private void startBot(){
        botThread = new Thread(() -> {
            try {
                botsApi = new TelegramBotsApi(DefaultBotSession.class);
                bot = new TelegramBot(this);
                botsApi.registerBot(bot);
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
        langFile = new File(getDataFolder(), "languages" + File.separator + getConfig().getString("language") + ".yml");
        if (!langFile.exists()) {
            langFile = new File(getDataFolder(), "languages" + File.separator + "en_US.yml");
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }
}
