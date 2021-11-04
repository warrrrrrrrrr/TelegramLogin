package it.ivirus.telegramlogin.spigot.command.telegramsubcommand;

import it.ivirus.telegramlogin.spigot.command.SubCommand;
import it.ivirus.telegramlogin.util.LangConstants;
import org.bukkit.command.CommandSender;

public class HelpSubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(LangConstants.INGAME_HELP_PLAYER.getFormattedString());

        if (sender.hasPermission("telegramlogin.admin")) {
            sender.sendMessage(LangConstants.INGAME_HELP_ADMIN.getFormattedString());
        }
    }
}
