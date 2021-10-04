package it.ivirus.telegramlogin.database;

import it.ivirus.telegramlogin.TelegramLogin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends SqlManager {

    public SQLite(TelegramLogin plugin) {
        super(plugin);
    }

    @Override
    public Connection getJdbcUrl() throws SQLException {
        String SQLITE_FILE_NAME = "database.db";
        String dataUrl = plugin.getDataFolder() + File.separator + "data" + File.separator + SQLITE_FILE_NAME;
        String url = "jdbc:sqlite:" + dataUrl;
        File dataFolder = new File(dataUrl);
        if (!dataFolder.exists())
            dataFolder.getParentFile().mkdirs();
        return DriverManager.getConnection(url);
    }

}
