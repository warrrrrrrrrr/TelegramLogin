package it.ivirus.telegramlogin.spigot.command.telegramsubcommand;

import it.ivirus.telegramlogin.spigot.command.SubCommand;
import it.ivirus.telegramlogin.util.LangConstants;
import it.ivirus.telegramlogin.util.MessageFactory;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ChangeChatIdSubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("telegramlogin.admin")) {
            sender.sendMessage(LangConstants.NOPERMISSION.getFormattedString());
            return;
        }
        if (args.length != 3) {
            sender.sendMessage(LangConstants.CHANGE_CHATID_USAGE.getFormattedString());
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(LangConstants.TARGET_OFFLINE.getFormattedString());
            return;
        }
        if (!NumberUtils.isDigits(args[2])) {
            sender.sendMessage(LangConstants.INVALID_VALUE.getFormattedString());
            return;
        }

        if (playerData.getPlayerCache().containsKey(target.getUniqueId())) {
            playerData.getPlayerCache().get(target.getUniqueId()).setChatID(args[2]);
        }
        plugin.getSql().getTelegramPlayer(target.getUniqueId().toString()).whenComplete((telegramPlayer, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }).thenAccept(telegramPlayer -> {
            if (telegramPlayer != null) {
                plugin.getSql().setChatId(target.getUniqueId().toString(), args[2]);
                sender.sendMessage(LangConstants.CHATID_CHANGED.getFormattedString());
            } else {
                sender.sendMessage(LangConstants.TARGET_WITHOUT_TELEGRAM_LOGIN.getFormattedString());
            }
        });
    }
}
