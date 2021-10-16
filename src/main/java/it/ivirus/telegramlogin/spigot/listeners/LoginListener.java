package it.ivirus.telegramlogin.spigot.listeners;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.data.PlayerData;
import it.ivirus.telegramlogin.util.MessageFactory;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.util.LangConstants;
import it.ivirus.telegramlogin.util.TelegramPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.UUID;

public class LoginListener implements Listener {
    private final TelegramLogin plugin;
    private final TelegramBot bot;
    private final PlayerData playerData = PlayerData.getInstance();

    public LoginListener(TelegramLogin plugin, TelegramBot bot) {
        this.plugin = plugin;
        this.bot = bot;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (playerData.getPlayerCache().containsKey(player.getUniqueId())){
            TelegramPlayer telegramPlayer = playerData.getPlayerCache().get(player.getUniqueId());
            if (telegramPlayer.isLocked()){
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString());
                return;
            }
            playerData.getPlayerInLogin().put(player.getUniqueId(), telegramPlayer);
            try {
                bot.execute(MessageFactory.loginRequest(telegramPlayer.getPlayerUUID(), telegramPlayer.getChatID(), event.getRealAddress().toString()));
                player.sendMessage(LangConstants.WAIT_FOR_LOGIN_CONFIRM.getFormattedString());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }
        plugin.getSql().getTelegramPlayer(player.getUniqueId().toString()).whenComplete((telegramPlayer, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }).thenAccept(telegramPlayer -> {
            if (telegramPlayer == null) {
                if (plugin.getConfig().getBoolean("2FA.enabled")) return;
                playerData.getPlayerWaitingForChatid().add(player.getUniqueId());
                player.sendMessage(LangConstants.ADD_CHATID.getFormattedString().replaceAll("%bot_tag%", bot.getBotUsername()));
            } else {
                playerData.getPlayerCache().put(player.getUniqueId(), telegramPlayer);
                if (telegramPlayer.isLocked()){
                    Bukkit.getScheduler().runTaskLater(plugin, () -> player.kickPlayer(LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString()), 1);
                    //event.disallow(PlayerLoginEvent.Result.KICK_OTHER, LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString());
                    return;
                }
                playerData.getPlayerInLogin().put(player.getUniqueId(), telegramPlayer);
                try {
                    bot.execute(MessageFactory.loginRequest(telegramPlayer.getPlayerUUID(), telegramPlayer.getChatID(), event.getRealAddress().toString()));
                    player.sendMessage(LangConstants.WAIT_FOR_LOGIN_CONFIRM.getFormattedString());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
