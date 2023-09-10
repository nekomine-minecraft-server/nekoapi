package net.nekomine.spigot;

import net.nekomine.spigot.board.BoardService;
import net.nekomine.spigot.board.BoardServiceImpl;
import net.nekomine.spigot.npc.NpcService;
import net.nekomine.spigot.npc.NpcServiceImpl;
import net.nekomine.spigot.state.StateService;
import net.nekomine.spigot.state.waiting.WaitingState;
import net.nekomine.spigot.tag.TagServiceImpl;
import net.nekomine.spigot.tag.TagService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SpigotApiPlugin extends JavaPlugin {

    private final List<Service> services = new ArrayList<>();

    @Override
    public void onEnable() {
        BoardServiceImpl boardService = new BoardServiceImpl(this);
        TagServiceImpl tagService = new TagServiceImpl(this);
        NpcService npcService = new NpcServiceImpl(this);

        services.add(boardService);
        services.add(tagService);

        Bukkit.getServicesManager().register(BoardService.class, boardService, this, ServicePriority.Normal);
        Bukkit.getServicesManager().register(TagService.class, tagService, this, ServicePriority.Normal);
        Bukkit.getServicesManager().register(NpcService.class, npcService, this, ServicePriority.Normal);

        services.forEach(Service::enable);

        StateService stateService = new StateServiceImpl();
        Bukkit.getServicesManager().register(StateService.class, stateService, this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
        services.forEach(Service::disable);
    }
}