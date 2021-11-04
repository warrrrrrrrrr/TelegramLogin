package it.ivirus.telegramlogin.spigot.command;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.data.PlayerData;
import it.ivirus.telegramlogin.spigot.command.telegramsubcommand.*;
import it.ivirus.telegramlogin.util.LangConstants;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class TelegramCommandHandler implements CommandExecutor {
    private final TelegramLogin plugin;
    private final PlayerData playerData = PlayerData.getInstance();
    private final FileConfiguration langConfig;

    public TelegramCommandHandler(TelegramLogin plugin) {
        this.plugin = plugin;
        this.langConfig = plugin.getLangConfig();
        registerCommand("reload", new ReloadSubcmd());
        registerCommand("changechatid", new ChangeChatIdSubcmd());
        registerCommand("remove", new RemoveSubcmd());
        registerCommand("accounts", new AccountsSubcmd());
        registerCommand("help", new HelpSubcmd());
        registerCommand("2fa", new TwoFASubcmd());
    }

    @Getter
    private final Map<String, SubCommand> commands = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {

        if (args.length == 0 || !commands.containsKey(args[0].toLowerCase())) {
            sender.sendMessage(LangConstants.INGAME_HELP_PLAYER.getFormattedString());

            if (sender.hasPermission("telegramlogin.admin")) {
                sender.sendMessage(LangConstants.INGAME_HELP_ADMIN.getFormattedString());
            }

            return true;
        }

        commands.get(args[0].toLowerCase()).onCommand(sender, args);
        return true;
    }

    private void registerCommand(String cmd, SubCommand subCommand) {
        commands.put(cmd, subCommand);
    }
}
