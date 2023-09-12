package net.nekomine.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpigotServer implements Server {
    private String name;
    private List<String> gamers = new ArrayList<>();
    private List<String> spectators = new ArrayList<>();
    private int maxPlayers = 0;
    private int maxGameSlots = 0;
    boolean waitGamers = false;
    boolean waitSpectators = false;
    private String map;

    @Override
    public String getKey() {
        return name.toLowerCase();
    }

    @Override
    public int getOnline() {
        return gamers.size() + spectators.size();
    }

    @Override
    public List<String> getAllPlayers() {
        List<String> allPlayers = new ArrayList<>(gamers);
        allPlayers.addAll(spectators);
        return allPlayers;
    }
}
