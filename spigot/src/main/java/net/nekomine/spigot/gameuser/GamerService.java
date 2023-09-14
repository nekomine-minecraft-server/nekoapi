package net.nekomine.spigot.gameuser;

import net.nekomine.common.utility.Service;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface GamerService extends Service {

    Optional<Gamer> getGamer(@NotNull String playerName);

    List<Gamer> getOnlineGamers();

    List<Gamer> getAllGamers();

    List<Spectator> getOffline();
}
