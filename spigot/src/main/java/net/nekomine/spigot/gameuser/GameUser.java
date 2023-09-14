package net.nekomine.spigot.gameuser;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class GameUser implements Gamer, Spectator {
    private String name;
    private Player player;
    private GamerState gamerState;

    public GameUser(String name, GamerState gamerState) {
        this.name = name;
        this.gamerState = gamerState;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAlwaysFly() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    public enum GamerState {
        SPECTATOR, GAMER
    }
}
