package net.nekomine.spigot;

import net.nekomine.common.model.SpigotServer;
import net.nekomine.common.redis.impl.EnvRedisFactory;
import net.nekomine.common.service.impl.SpigotServerService;
import net.nekomine.common.utility.Service;
import net.nekomine.spigot.board.BoardService;
import net.nekomine.spigot.board.BoardServiceImpl;
import net.nekomine.spigot.npc.NpcService;
import net.nekomine.spigot.npc.NpcServiceImpl;
import net.nekomine.spigot.service.SpigotServerServiceImpl;
import net.nekomine.spigot.state.StateService;
import net.nekomine.spigot.tag.TagServiceImpl;
import net.nekomine.spigot.tag.TagService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RedissonClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SpigotApiPlugin extends JavaPlugin {
    private final SpigotServer spigotServer = new SpigotServer(getSpigotName(),
            new ArrayList<>(),
            new ArrayList<>(),
            Bukkit.getMaxPlayers(), 16, "world");

    private final List<Service> services = new ArrayList<>();

    @Override
    public void onEnable() {
        RedissonClient redissonClient = new EnvRedisFactory().create();
        BoardServiceImpl boardService = new BoardServiceImpl(this);
        TagServiceImpl tagService = new TagServiceImpl(this);
        NpcService npcService = new NpcServiceImpl(this);
        SpigotServerService spigotServerService = new SpigotServerServiceImpl(redissonClient, spigotServer, this);

        services.add(boardService);
        services.add(tagService);

        Bukkit.getServicesManager().register(BoardService.class, boardService, this, ServicePriority.Normal);
        Bukkit.getServicesManager().register(TagService.class, tagService, this, ServicePriority.Normal);
        Bukkit.getServicesManager().register(NpcService.class, npcService, this, ServicePriority.Normal);
        Bukkit.getServicesManager().register(SpigotServer.class, spigotServer, this, ServicePriority.Normal);
        Bukkit.getServicesManager().register(SpigotServerService.class, spigotServerService, this, ServicePriority.Normal);

        services.forEach(Service::enable);

        StateService stateService = new StateServiceImpl();
        Bukkit.getServicesManager().register(StateService.class, stateService, this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
        services.forEach(Service::disable);
    }

    /**
     * Получить имя сервера по папке
     */
    public String getSpigotName() {
        try {
            return Bukkit.getWorldContainer().getCanonicalFile().getName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
