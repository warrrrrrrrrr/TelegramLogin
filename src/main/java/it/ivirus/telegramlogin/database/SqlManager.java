package it.ivirus.telegramlogin.database;

import it.ivirus.telegramlogin.TelegramLogin;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SqlManager {
    protected final TelegramLogin plugin;
    protected final String TABLE_PLAYERS = "Player";
    @Getter
    private Connection connection;

    public SqlManager(TelegramLogin plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                return;
            }
            setConnection(getJdbcUrl());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createTables() throws SQLException {
        PreparedStatement data = getConnection().prepareStatement("create TABLE if not exists " + TABLE_PLAYERS + " " +
                "(PlayerUUID VARCHAR(100), IDChat VARCHAR(100) PRIMARY KEY," +
                "RegistrationDate DATETIME NOT NULL)");
        data.executeUpdate();
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected abstract Connection getJdbcUrl() throws SQLException, ClassNotFoundException;
}
