package net.nekomine.spigot.npc;

import net.nekomine.common.record.Skin;
import org.bukkit.plugin.Plugin;

public class NpcServiceImpl implements NpcService {
    private final Plugin plugin;

    public NpcServiceImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public PlayerNpc createPlayer() {
        return new PlayerNpcImpl(plugin);
    }

    @Override
    public PlayerNpc createPlayer(Skin skin) {
        PlayerNpc npc = new PlayerNpcImpl(plugin);
        npc.setSkin(skin);
        return npc;
    }
}
