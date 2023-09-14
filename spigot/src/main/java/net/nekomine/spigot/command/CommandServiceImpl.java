package net.nekomine.spigot.command;

import net.nekomine.common.utility.BaseService;
import net.nekomine.spigot.utility.NmsUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandServiceImpl extends BaseService implements CommandService {

    private CommandMap commandMap;

    @Override
    public void enable() {
        super.enable();

        try {
            Field commandMapField = NmsUtil.getCraftBukkit("CraftServer")
                    .getDeclaredField("commandMap");

            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Command createPlayerCommand(PlayerCommandExecutor playerCommandExecutor) {
        return new Command(playerCommandExecutor.getName(), playerCommandExecutor.getDescription(), playerCommandExecutor.getUsageMessage(), playerCommandExecutor.getAliases()) {
            @Override
            public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
                if (commandSender instanceof ConsoleCommandSender sender) {
                    sender.sendMessage("Команды игрока не доступны через консоль!");
                    return true;
                }

                if (commandSender instanceof Player player) {
                    playerCommandExecutor.execute(player, label, args);
                    return true;
                }

                return false;
            }
        };
    }

    @SuppressWarnings("ignore all")
    public void registerCommand(Command command) {
        String commandName = command.getName();

        List<String> commands = new ArrayList<>(command.getAliases());
        commands.add(commandName);
        try {
            Method register = SimpleCommandMap.class.getDeclaredMethod("register", String.class, Command.class, Boolean.TYPE, String.class);
            register.setAccessible(true);

            for (String cmd : commands) {
                register.invoke(commandMap, cmd, command, !(commandName.equals(cmd)), "nekoapi");
            }

            Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);

            Map<String, Command> map = (Map) knownCommands.get(commandMap);

            for (String cmd : commands) {
                map.put(cmd.toLowerCase(), command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
