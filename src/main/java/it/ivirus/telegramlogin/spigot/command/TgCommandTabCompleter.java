package it.ivirus.telegramlogin.spigot.command;

import it.ivirus.telegramlogin.TelegramLogin;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TgCommandTabCompleter implements TabCompleter {
    private final TelegramLogin plugin;

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        CommandExecutor commandExecutor = plugin.getCommand(command.getName()).getExecutor();
        TelegramCommandHandler tgCommandHandler = (TelegramCommandHandler) commandExecutor;
        if (args.length == 1) {
            return new ArrayList<>(tgCommandHandler.getCommands().keySet());
        }

        return null;
    }
}
