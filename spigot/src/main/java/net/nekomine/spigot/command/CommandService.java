package net.nekomine.spigot.command;

import org.bukkit.command.Command;

public interface CommandService {

    Command createPlayerCommand(PlayerCommandExecutor playerCommandExecutor);

    void registerCommand(Command command);

}
