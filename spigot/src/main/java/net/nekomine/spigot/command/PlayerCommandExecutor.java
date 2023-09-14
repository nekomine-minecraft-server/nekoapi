package net.nekomine.spigot.command;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public interface PlayerCommandExecutor {

    void execute(@NotNull Player player, @NotNull String label, @NotNull String[] args);

    String getName();

    default String getDescription() {
        return null;
    }

    default String getUsageMessage() {
        return null;
    }

    default List<String> getAliases(){
        return Collections.emptyList();
    }

    default @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }

    default @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        return Collections.emptyList();
    }
}
