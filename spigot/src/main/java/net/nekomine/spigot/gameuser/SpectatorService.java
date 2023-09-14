package net.nekomine.spigot.gameuser;

import net.nekomine.common.utility.Service;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface SpectatorService extends Service {
    void addSpectator(@NotNull String playerName);

    void removeSpectator(@NotNull String playerName);

    boolean isSpectator(@NotNull String playerName);

    List<Spectator> getOnlineSpectators();

    List<Spectator> getAllSpectators();

    List<Spectator> getOfflineSpectators();
}
