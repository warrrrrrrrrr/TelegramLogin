package it.ivirus.telegramlogin.spigot.command;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.data.PlayerData;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    protected final TelegramLogin plugin = TelegramLogin.getInstance();
    protected final PlayerData playerData = PlayerData.getInstance();
    public abstract void onCommand(CommandSender sender, String[] args);
}
