package it.ivirus.telegramlogin.spigot.listeners;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.data.PlayerData;
import it.ivirus.telegramlogin.telegram.KeyboardFactory;
import it.ivirus.telegramlogin.telegram.MessageFactory;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.util.LangConstants;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Date;

public class PlayerListener implements Listener {
    private final TelegramLogin plugin;
    private final PlayerData playerData = PlayerData.getInstance();
    private final TelegramBot bot;

    public PlayerListener(TelegramLogin plugin, TelegramBot bot) {
        this.plugin = plugin;
        this.bot = bot;
    }

    @EventHandler
    public void onPlayerInsertChatID(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (playerData.getPlayerWaitingForChatid().contains(player.getUniqueId())) {
            event.setCancelled(true);
            String chatId = event.getMessage();
            if (chatId.equalsIgnoreCase("abort")) {
                playerData.getPlayerWaitingForChatid().remove(player.getUniqueId());
                if (plugin.getConfig().getBoolean("2FA.enabled")) {
                    player.sendMessage(LangConstants.OPERATION_ABORTED.getFormattedString());
                    return;
                }
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.kickPlayer(LangConstants.KICK_LOG_AGAIN.getFormattedString()), 1);
                return;
            }
            if (!NumberUtils.isNumber(chatId)) {
                player.sendMessage(LangConstants.INVALID_VALUE.getFormattedString());
                return;
            }
            Date date = new Date(System.currentTimeMillis());
            plugin.getSql().getTelegramPlayerByChatId(chatId).whenComplete((telegramPlayer, throwable) -> {
                if (throwable != null)
                    throwable.printStackTrace();
            }).thenAccept(telegramPlayer -> {
                if (telegramPlayer == null) {
                    plugin.getSql().addPlayerLogin(player.getUniqueId().toString(), chatId, date);
                    try {
                        bot.execute(MessageFactory.simpleMessageButtons(chatId, LangConstants.TG_ADD_MESSAGE.getString(), KeyboardFactory.addConfirmButtons(player.getUniqueId().toString(), chatId)));
                        player.sendMessage(LangConstants.WAIT_FOR_CONFIRM.getFormattedString());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    player.sendMessage(LangConstants.CHATID_ALREADY_USED.getFormattedString());
                    return;
                }
            });
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (playerData.getPlayerInLogin().containsKey(event.getPlayer().getUniqueId()) || playerData.getPlayerWaitingForChatid().contains(event.getPlayer().getUniqueId())) {
            Location from = event.getFrom();
            Location to = event.getTo();
            double x = Math.floor(from.getX());
            double z = Math.floor(from.getZ());

            if (to == null) {
                return;
            }
            if (Math.floor(to.getX()) != x || Math.floor(to.getZ()) != z) {
                x += .5;
                z += .5;
                event.getPlayer().teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
            }
        }
    }
}
