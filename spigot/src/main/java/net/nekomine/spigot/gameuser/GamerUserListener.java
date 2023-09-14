package net.nekomine.spigot.gameuser;

import lombok.RequiredArgsConstructor;
import net.nekomine.spigot.state.StateService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Map;

@RequiredArgsConstructor
public class GamerUserListener implements Listener {
    private final SpectatorService spectatorService;
    private final StateService stateService;
    private final Map<String, GameUser> gameUserMap;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        String userName = event.getName();

        if (gameUserMap.containsKey(userName)) {
            GameUser gameUser = gameUserMap.get(userName);
            GameUser.GamerState state = gameUser.getGamerState();

            if (state == GameUser.GamerState.SPECTATOR) {
                spectatorService.addSpectator(userName);
            }
            return;
        }

        if (stateService.getCurrentState().waitGamers()) {
            gameUserMap.put(userName, new GameUser(userName, GameUser.GamerState.GAMER));
        } else {
            gameUserMap.put(userName, new GameUser(userName, GameUser.GamerState.SPECTATOR));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String userName = player.getName();
        GameUser user = gameUserMap.get(player.getName());

        user.setName(userName);
        user.setPlayer(player);

        if (stateService.getCurrentState().waitGamers()) {
            spectatorService.removeSpectator(userName);
        } else {
            spectatorService.addSpectator(userName);
        }
    }
}