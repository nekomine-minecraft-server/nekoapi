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
    private List<String> gamers;
    private List<String> spectators;
    private int maxPlayers;
    private int maxGameSlots;
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
