package it.ivirus.telegramlogin.spigot.command.telegramsubcommand;

import it.ivirus.telegramlogin.spigot.command.SubCommand;
import it.ivirus.telegramlogin.util.LangConstants;
import it.ivirus.telegramlogin.util.PluginMessageAction;
import it.ivirus.telegramlogin.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TwoFASubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(LangConstants.INGAME_ONLY_PLAYER.getFormattedString());
            return;
        }
        Player player = (Player) sender;
        if (!plugin.getConfig().getBoolean("2FA.enabled")){
            sender.sendMessage(LangConstants.INGAME_TWOFA_DISABLED.getFormattedString());
            return;
        }
        if (playerData.getPlayerCache().containsKey(player.getUniqueId())){
            sender.sendMessage(LangConstants.INGAME_SENDER_ALREADY_2FA.getFormattedString());
            return;
        }
        playerData.getPlayerWaitingForChatid().add(player.getUniqueId());
        if (plugin.getConfig().getBoolean("bungee"))
            Util.sendPluginMessage(player, PluginMessageAction.ADD);
        player.sendMessage(LangConstants.INGAME_ADD_CHATID.getFormattedString().replaceAll("%bot_tag%", plugin.getConfig().getString("bot.name")));
        player.sendMessage(LangConstants.INGAME_ABORT_2FA.getFormattedString());
    }
}
