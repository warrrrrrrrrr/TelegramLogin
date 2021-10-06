package it.ivirus.telegramlogin.spigot.listeners;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.data.PlayerData;
import it.ivirus.telegramlogin.telegram.MessageFactory;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.util.LangConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        plugin.getSql().getTelegramPlayer(player.getUniqueId().toString()).whenComplete((telegramPlayer, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }).thenAccept(telegramPlayer -> {
            if (telegramPlayer == null) {
                if (plugin.getConfig().getBoolean("2FA.enabled")) return;
                playerData.getPlayerWaitingForChatid().add(player.getUniqueId());
                player.sendMessage(LangConstants.ADD_CHATID.getFormattedString());
            } else {
                playerData.getPlayerInLogin().put(player.getUniqueId(), telegramPlayer);
                try {
                    bot.execute(MessageFactory.addConfirm(telegramPlayer.getPlayerUUID(), telegramPlayer.getChatID()));
                    player.sendMessage(LangConstants.WAIT_FOR_LOGIN_CONFIRM.getFormattedString());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
