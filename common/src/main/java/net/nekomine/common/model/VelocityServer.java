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
public class VelocityServer implements Server {
    private String name;
    private List<String> players;
    private int maxPlayers;

    @Override
    public String getId() {
        return name.toLowerCase();
    }

    @Override
    public int getOnline() {
        return players.size();
    }

    @Override
    public List<String> getAllPlayers() {
        return new ArrayList<>(players);
    }
}
