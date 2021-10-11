package it.ivirus.telegramlogin.spigot.command;

import it.ivirus.telegramlogin.TelegramLogin;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    protected final TelegramLogin plugin = TelegramLogin.getInstance();
    public abstract boolean onCommand(CommandSender sender, String[] args);
}
