package net.nekomine.spigot;

import net.nekomine.common.model.SpigotServer;
import net.nekomine.common.redis.impl.EnvRedisFactory;
import net.nekomine.common.service.impl.SpigotServerService;
import net.nekomine.common.service.impl.VelocityServerService;
import net.nekomine.common.utility.Service;
import net.nekomine.spigot.board.BoardServiceImpl;
import net.nekomine.spigot.gameuser.GameUser;
import net.nekomine.spigot.gameuser.GameUserService;
import net.nekomine.spigot.gameuser.GamerService;
import net.nekomine.spigot.gameuser.SpectatorService;
import net.nekomine.spigot.npc.NpcService;
import net.nekomine.spigot.npc.NpcServiceImpl;
import net.nekomine.spigot.service.SpigotServerServiceImpl;
import net.nekomine.spigot.state.StateService;
import net.nekomine.spigot.state.unknown.UnknownState;
import net.nekomine.spigot.tag.TagServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RedissonClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unused")
public final class SpigotApiPlugin extends JavaPlugin {
    private final SpigotServer spigotServer = new SpigotServer();
    private final List<Service> services = new ArrayList<>();
    private final Map<String, GameUser> gameUserMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    @Override
    public void onEnable() {
        spigotServer.setName(getSpigotName());

        RedissonClient redissonClient = registerService(RedissonClient.class, new EnvRedisFactory().create());
        BoardServiceImpl boardService = registerService(BoardServiceImpl.class, new BoardServiceImpl(this));
        TagServiceImpl tagService = registerService(TagServiceImpl.class, new TagServiceImpl(this));
        NpcService npcService = registerService(NpcServiceImpl.class, new NpcServiceImpl(this));

        StateService stateService = registerService(StateService.class, new StateServiceImpl());
        stateService.addState(new UnknownState());

        GameUserService gameUserService = new GameUserService(stateService, this, gameUserMap);
        registerService(SpectatorService.class, gameUserService);
        registerService(GamerService.class, gameUserService);

        SpigotServerService spigotServerService = registerService(SpigotServerService.class, new SpigotServerServiceImpl(redissonClient, stateService, spigotServer, this));
        VelocityServerService velocityServerService = registerService(VelocityServerService.class, new VelocityServerService(redissonClient));

        services.forEach(Service::enable);
    }

    private <T> T registerService(Class<T> serviceClass, T t) {
        return registerService(serviceClass, t, ServicePriority.Normal);
    }

    @SuppressWarnings("ignore all")
    private <T> T registerService(Class<T> serviceClass, T t, ServicePriority priority) {
        if (t instanceof Service service) {
            services.add(service);
        }

        Bukkit.getServicesManager().register(serviceClass, t, this, priority);

        return t;
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
