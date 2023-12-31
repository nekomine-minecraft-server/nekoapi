package net.nekocraft.spigot.impl.gameuser;

import net.nekomine.common.utility.BaseService;
import net.nekomine.spigot.gameuser.Gamer;
import net.nekomine.spigot.gameuser.GamerService;
import net.nekomine.spigot.gameuser.Spectator;
import net.nekomine.spigot.gameuser.SpectatorService;
import net.nekomine.spigot.state.StateService;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameUserService extends BaseService implements GamerService, SpectatorService {
    private final StateService stateService;
    private final Plugin plugin;
    private final Map<String, GameUser> gameUserMap;
    private SpectatorTasks spectatorTasks;
    private GamerUserListener gamerUserListener;
    private SpectatorListener spectatorListener;

    public GameUserService(StateService stateService, Plugin plugin, Map<String, GameUser> gameUserMap) {
        this.stateService = stateService;
        this.plugin = plugin;
        this.gameUserMap = gameUserMap;
    }

    @Override
    public void enable() {
        super.enable();

        spectatorTasks = new SpectatorTasks(this);
        gamerUserListener = new GamerUserListener(this, stateService, gameUserMap);
        spectatorListener = new SpectatorListener(this, spectatorTasks, stateService);

        Bukkit.getPluginManager().registerEvents(gamerUserListener, plugin);
        Bukkit.getPluginManager().registerEvents(spectatorListener, plugin);

        spectatorTasks.runTaskTimerAsynchronously(plugin, 15L, 15L);
    }

    @Override
    public void disable() {
        super.disable();

        HandlerList.unregisterAll(gamerUserListener);
        HandlerList.unregisterAll(spectatorListener);

        spectatorTasks.clearAll();
        spectatorTasks.cancel();
    }

    @Override
    public @Nullable Optional<Gamer> getGamer(@NotNull String playerName) {
        GameUser gameUser = gameUserMap.get(playerName);

        if (gameUser == null) {
            return Optional.empty();
        }

        if (gameUser.getGamerState() != GameUser.GamerState.GAMER) {
            return Optional.empty();
        }

        return Optional.of(gameUser);
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
    public @Nullable Optional<Spectator> getSpectator(@NotNull String playerName) {
        GameUser gameUser = gameUserMap.get(playerName);

        if (gameUser == null) {
            return Optional.empty();
        }

        if (gameUser.getGamerState() != GameUser.GamerState.SPECTATOR) {
            return Optional.empty();
        }

        return Optional.of(gameUser);
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
