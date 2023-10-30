package net.nekocraft.spigot.impl;

import net.nekocraft.common.impl.redis.EnvRedisFactory;
import net.nekocraft.common.impl.service.VelocityServerServiceImpl;
import net.nekocraft.spigot.impl.board.BoardServiceImpl;
import net.nekocraft.spigot.impl.command.CommandServiceImpl;
import net.nekocraft.spigot.impl.command.ServerStateCommandExecutor;
import net.nekocraft.spigot.impl.gameuser.GameUser;
import net.nekocraft.spigot.impl.gameuser.GameUserService;
import net.nekocraft.spigot.impl.hub.HubState;
import net.nekocraft.spigot.impl.npc.NpcServiceImpl;
import net.nekocraft.spigot.impl.server.SpigotServerServiceImpl;
import net.nekomine.common.model.SpigotServer;
import net.nekomine.common.service.SpigotServerService;
import net.nekomine.common.service.VelocityServerService;
import net.nekomine.common.utility.Service;
import net.nekomine.spigot.command.CommandService;
import net.nekomine.spigot.npc.NpcService;
import net.nekomine.spigot.state.StateService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RedissonClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unused")
public final class SpigotApiPlugin extends JavaPlugin {
    private final SpigotServer spigotServer = new SpigotServer();
    private final Map<Class<?>, Service> services = new HashMap<>();
    private final Map<String, GameUser> gameUserMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    @Override
    public void onEnable() {
        registerServices();
    }

    private void registerServices() {
        spigotServer.setName(getSpigotName());

        RedissonClient redissonClient = registerService(RedissonClient.class, new EnvRedisFactory().create());
        BoardServiceImpl boardService = registerService(BoardServiceImpl.class, new BoardServiceImpl(this));
        //TagServiceImpl tagService = registerService(TagServiceImpl.class, new TagServiceImpl(this));
        NpcService npcService = registerService(NpcServiceImpl.class, new NpcServiceImpl(this));
        CommandService commandService = registerService(CommandService.class, new CommandServiceImpl());
        StateService stateService = registerService(StateService.class, new StateServiceImpl());
        registerService(GameUserService.class, new GameUserService(stateService, this, gameUserMap));
        SpigotServerService spigotServerService = registerService(SpigotServerService.class, new SpigotServerServiceImpl(redissonClient, stateService, spigotServer, this));
        VelocityServerService velocityServerService = registerService(VelocityServerService.class, new VelocityServerServiceImpl(redissonClient));

        services.values().forEach(Service::enable);

        registerCommand(commandService);

        stateService.addState(new HubState(this, boardService, velocityServerService));
        stateService.nextState();
    }

    private void registerCommand(CommandService commandService) {
        commandService.registerCommand(commandService.createPlayerCommand(new ServerStateCommandExecutor(spigotServer)));
    }

    private <T> T registerService(Class<T> serviceClass, T t) {
        return registerService(serviceClass, t, ServicePriority.Normal);
    }

    @SuppressWarnings("ignore all")
    private <T> T registerService(Class<T> serviceClass, T t, ServicePriority priority) {
        if (t instanceof Service service) {
            services.put(serviceClass, service);
        }

        Bukkit.getServicesManager().register(serviceClass, t, this, priority);

        return t;
    }

    @Override
    public void onDisable() {
        services.values().forEach(service -> {
            System.out.println(service.getClass());

            service.disable();
        });
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
