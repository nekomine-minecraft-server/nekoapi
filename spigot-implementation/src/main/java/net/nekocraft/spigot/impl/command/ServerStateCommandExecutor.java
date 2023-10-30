package net.nekocraft.spigot.impl.command;

import net.nekomine.common.model.SpigotServer;
import net.nekomine.spigot.command.PlayerCommandExecutor;
import net.nekomine.spigot.utility.ModelFormat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ServerStateCommandExecutor implements PlayerCommandExecutor {
    private final SpigotServer spigotServer;

    public ServerStateCommandExecutor(SpigotServer spigotServer) {
        this.spigotServer = spigotServer;
    }

    @Override
    public void execute(@NotNull Player player, @NotNull String label, @NotNull String[] args) {
        ModelFormat.modelToListString(spigotServer).forEach(player::sendMessage);
    }

    @Override
    public String getName() {
        return "serverstate";
    }
}
