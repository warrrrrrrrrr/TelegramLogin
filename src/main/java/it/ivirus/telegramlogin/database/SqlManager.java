package it.ivirus.telegramlogin.database;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.util.TelegramPlayer;
import lombok.Getter;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

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
                "(PlayerUUID VARCHAR(100), ChatID VARCHAR(100) PRIMARY KEY," +
                "RegistrationDate DATETIME NOT NULL)");
        data.executeUpdate();
    }

    public void addPlayerLogin(String playerUUID, String chatId, Date registrationDate) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " + TABLE_PLAYERS + " (PlayerUUID, ChatID, RegistrationDate)" +
                " values (?,?,?)")) {
            statement.setString(1, playerUUID);
            statement.setString(2, chatId);
            statement.setDate(3, registrationDate);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public CompletableFuture<TelegramPlayer> getTelegramPlayer(String playerUUID) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + TABLE_PLAYERS + " WHERE PlayerUUID=?")) {
                statement.setString(1, playerUUID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new TelegramPlayer(resultSet.getString("PlayerUUID"), resultSet.getString("ChatID"), resultSet.getDate("RegistrationDate"));
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }, plugin.getExecutor());
    }

    public CompletableFuture<TelegramPlayer> getTelegramPlayerByChatId(String chatId) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + TABLE_PLAYERS + " WHERE ChatID=?")) {
                statement.setString(1, chatId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new TelegramPlayer(resultSet.getString("PlayerUUID"), resultSet.getString("ChatID"), resultSet.getDate("RegistrationDate"));
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }, plugin.getExecutor());
    }

    public void removePlayerLogin(String playerUUID) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " + TABLE_PLAYERS + " WHERE PlayerUUID=?")) {
            statement.setString(1, playerUUID);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected abstract Connection getJdbcUrl() throws SQLException, ClassNotFoundException;
}
