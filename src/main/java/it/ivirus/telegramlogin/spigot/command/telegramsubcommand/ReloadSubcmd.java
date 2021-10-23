package it.ivirus.telegramlogin.spigot.command.telegramsubcommand;

import it.ivirus.telegramlogin.spigot.command.SubCommand;
import it.ivirus.telegramlogin.util.LangConstants;
import org.bukkit.command.CommandSender;

public class ReloadSubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("telegramlogin.admin")) {
            sender.sendMessage(LangConstants.NOPERMISSION.getFormattedString());
            return;
        }
        plugin.reloadConfig();
        plugin.loadLangConfig();
        sender.sendMessage(LangConstants.RELOAD_EXECUTED.getFormattedString());
    }
}
