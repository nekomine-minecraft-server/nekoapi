package net.nekomine.spigot.state.game;

import net.nekomine.spigot.gameuser.Gamer;
import net.nekomine.spigot.gameuser.GamerService;
import net.nekomine.spigot.gameuser.Spectator;
import net.nekomine.spigot.gameuser.SpectatorService;
import net.nekomine.spigot.state.State;

import java.util.List;

public class GameState implements State {
    protected final SpectatorService spectatorService;
    protected final GamerService gamerService;

    public GameState(SpectatorService spectatorService, GamerService gamerService) {
        this.spectatorService = spectatorService;
        this.gamerService = gamerService;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public List<String> getSpectators() {
        return spectatorService.getOnlineSpectators().stream().map(Spectator::getName).toList();
    }

    @Override
    public List<String> getGamers() {
        return gamerService.getOnlineGamers().stream().map(Gamer::getName).toList();
    }

    @Override
    public boolean waitGamers() {
        return false;
    }

    @Override
    public boolean waitSpectators() {
        return true;
    }
}
