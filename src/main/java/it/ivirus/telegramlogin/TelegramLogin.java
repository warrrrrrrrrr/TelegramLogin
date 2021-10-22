package it.ivirus.telegramlogin;

import it.ivirus.telegramlogin.data.Task;
import it.ivirus.telegramlogin.database.SQLite;
import it.ivirus.telegramlogin.database.SqlManager;
import it.ivirus.telegramlogin.database.remote.MySQL;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.util.Secure;
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

        if (!this.checkPrerequisites())
            return;
        if (!new Secure(this, getConfig().getString("license"), "http://hoxija.it:8080/api/client", "6bafe710273471ab74290a5deca50c52423b5964").verify()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
        }
        this.setupDb();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "hxj:telegramlogin");
        this.startBot();
        new Task(this).startClearCacheTask();
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
        if (getConfig().getBoolean("MySQL.Enable")) {
            MySQL mysql = (MySQL) sql;
            mysql.closePool();
        }
        botThread.stop();
        Bukkit.getScheduler().cancelTasks(this);
    }

    private boolean checkPrerequisites() {
        String license = getConfig().getString("license");
        String bottoken = getConfig().getString("bot.token");
        boolean status = false;
        if (license.equalsIgnoreCase("Put your license here")) {
            System.out.println("Insert your license into config.yml");
            status = true;
        }
        if (bottoken.equalsIgnoreCase("YourToken")) {
            System.out.println("Insert your bot token into config.yml");
            status = true;
        }

        if (status) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return false;
        }
        return true;
    }

    private void startBot() {
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
