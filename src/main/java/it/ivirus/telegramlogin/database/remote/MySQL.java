package it.ivirus.telegramlogin.database.remote;

import com.zaxxer.hikari.HikariDataSource;
import it.ivirus.telegramlogin.TelegramLogin;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL extends ConnectionPoolManager {
    private final String database, username, password, host;
    private final boolean ssl;
    private final int port;

    public MySQL(TelegramLogin plugin, String database, String username, String password, String host, int port, boolean ssl) {
        super(plugin);
        this.database = database;
        this.host = host;
        this.password = password;
        this.username = username;
        this.port = port;
        this.ssl = ssl;
    }

    @Override
    public Connection getJdbcUrl() throws SQLException {
        getHikariConfig().setJdbcUrl(
                "jdbc:mysql://" +
                        this.host +
                        ":" +
                        this.port +
                        "/" +
                        this.database +
                        "?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useSSL=" +
                        this.ssl
        );
        getHikariConfig().setDriverClassName("com.mysql.jdbc.Driver");
        getHikariConfig().setUsername(this.username);
        getHikariConfig().setPassword(this.password);
        setDataSource(new HikariDataSource(getHikariConfig()));

        return getDataSource().getConnection();
    }

}
