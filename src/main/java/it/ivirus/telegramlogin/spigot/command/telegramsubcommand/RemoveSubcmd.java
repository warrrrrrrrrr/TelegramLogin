package it.ivirus.telegramlogin.spigot.command.telegramsubcommand;

import it.ivirus.telegramlogin.spigot.command.SubCommand;
import it.ivirus.telegramlogin.util.LangConstants;
import org.apache.commons.codec.language.bm.Lang;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveSubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 1){
            if (!(sender instanceof Player)){
                sender.sendMessage(LangConstants.ONLY_PLAYER.getFormattedString());
                return;
            }
            Player player = (Player) sender;
            if (!plugin.getConfig().getBoolean("2FA.enabled")){
                sender.sendMessage(LangConstants.CANNOT_REMOVE.getFormattedString());
                return;
            }
            if (!playerData.getPlayerCache().containsKey(player.getUniqueId())){
                sender.sendMessage(LangConstants.SENDER_WITHOUT_TELEGRAMLOGIN.getFormattedString());
                return;
            }
            playerData.getPlayerCache().remove(player.getUniqueId());
            plugin.getSql().removePlayerLogin(player.getUniqueId().toString());
            player.sendMessage(LangConstants.ACCOUNT_DISCONNECTED.getFormattedString());
            return;
        }
        if (args.length > 1){
            if (!sender.hasPermission("telegramlogin.admin")) {
                sender.sendMessage(LangConstants.NOPERMISSION.getFormattedString());
                return;
            }
            if (args.length != 2) {
                sender.sendMessage(LangConstants.REMOVE_USAGE.getFormattedString());
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(LangConstants.TARGET_OFFLINE.getFormattedString());
                return;
            }

            playerData.getPlayerCache().remove(target.getUniqueId());

            plugin.getSql().getTelegramPlayer(target.getUniqueId().toString()).whenComplete((telegramPlayer, throwable) -> {
                if (throwable != null) {
                    throwable.printStackTrace();
                }
            }).thenAccept(telegramPlayer -> {
                if (telegramPlayer != null) {
                    plugin.getSql().removePlayerLogin(target.getUniqueId().toString());
                    sender.sendMessage(LangConstants.TARGET_REMOVED.getFormattedString().replaceAll("%target%", target.getName()));
                } else {
                    sender.sendMessage(LangConstants.TARGET_WITHOUT_TELEGRAM_LOGIN.getFormattedString());
                }
            });
        }
    }
}
