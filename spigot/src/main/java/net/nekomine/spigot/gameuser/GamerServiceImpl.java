package net.nekomine.spigot.gameuser;

import net.nekomine.common.utility.BaseService;
import net.nekomine.spigot.state.StateService;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class GamerServiceImpl extends BaseService implements GamerService, SpectatorService {
    private final StateService stateService;
    private final Plugin plugin;
    private final Map<String, GameUser> gameUserMap;
    private SpectatorTasks spectatorTasks;
    private GamerUserListener gamerUserListener;
    private SpectatorListener spectatorListener;

    public GamerServiceImpl(StateService stateService, Plugin plugin, Map<String, GameUser> gameUserMap) {
        this.stateService = stateService;
        this.plugin = plugin;
        this.gameUserMap = gameUserMap;
    }

    @Override
    public void enable() {
        super.enable();

        gamerUserListener = new GamerUserListener(this, stateService, gameUserMap);
        spectatorListener = new SpectatorListener(this, spectatorTasks);

        Bukkit.getPluginManager().registerEvents(gamerUserListener, plugin);
        Bukkit.getPluginManager().registerEvents(spectatorListener, plugin);

        spectatorTasks = new SpectatorTasks(this);
        spectatorTasks.runTaskTimerAsynchronously(plugin, 15L, 15L);
    }

    @Override
    public void disable() {
        super.enable();

        HandlerList.unregisterAll(gamerUserListener);
        HandlerList.unregisterAll(spectatorListener);

        spectatorTasks.clearAll();
        spectatorTasks.cancel();
    }

    @Override
    public List<Gamer> getOnlineGamers() {
        return gameUserMap.values()
                .stream()
                .filter(gameUser -> gameUser.getPlayer() != null)
                .filter(gameUser -> gameUser.getPlayer().isOnline())
                .filter(gameUser -> gameUser.getGamerState() == GameUser.GamerState.GAMER)
                .map(gameUser -> (Gamer) gameUser)
                .toList();
    }

    @Override
    public List<Gamer> getAllGamers() {
        return gameUserMap.values()
                .stream()
                .filter(gameUser -> gameUser.getGamerState() == GameUser.GamerState.GAMER)
                .map(gameUser -> (Gamer) gameUser)
                .toList();
    }

    @Override
    public List<Spectator> getOffline() {
        return gameUserMap.values()
                .stream()
                .filter(gameUser -> gameUser.getPlayer() == null || !gameUser.getPlayer().isOnline())
                .filter(gameUser -> gameUser.getGamerState() == GameUser.GamerState.GAMER)
                .map(gameUser -> (Spectator) gameUser)
                .toList();
    }

    @Override
    public void addSpectator(@NotNull String playerName) {
        gameUserMap.get(playerName).setGamerState(GameUser.GamerState.SPECTATOR);

        //todo: set scoreboard, teleport
    }

    @Override
    public void removeSpectator(@NotNull String playerName) {
        gameUserMap.get(playerName).setGamerState(GameUser.GamerState.GAMER);

        //todo: normalize player
    }

    @Override
    public boolean isSpectator(@NotNull String playerName) {
        return gameUserMap.get(playerName).getGamerState() == GameUser.GamerState.SPECTATOR;
    }

    @Override
    public List<Spectator> getOnlineSpectators() {
        return gameUserMap.values()
                .stream()
                .filter(gameUser -> gameUser.getPlayer() != null)
                .filter(gameUser -> gameUser.getPlayer().isOnline())
                .filter(gameUser -> gameUser.getGamerState() == GameUser.GamerState.SPECTATOR)
                .map(gameUser -> (Spectator) gameUser)
                .toList();
    }

    @Override
    public List<Spectator> getAllSpectators() {
        return gameUserMap.values()
                .stream()
                .filter(gameUser -> gameUser.getGamerState() == GameUser.GamerState.SPECTATOR)
                .map(gameUser -> (Spectator) gameUser)
                .toList();
    }

    @Override
    public List<Spectator> getOfflineSpectators() {
        return gameUserMap.values()
                .stream()
                .filter(gameUser -> gameUser.getPlayer() == null || !gameUser.getPlayer().isOnline())
                .filter(gameUser -> gameUser.getGamerState() == GameUser.GamerState.SPECTATOR)
                .map(gameUser -> (Spectator) gameUser)
                .toList();
    }
}
