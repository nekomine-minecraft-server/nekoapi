package net.nekomine.spigot.gameuser;

import net.nekomine.common.utility.Service;

import java.util.List;

public interface GamerService extends Service {

    List<Gamer> getOnlineGamers();

    List<Gamer> getAllGamers();

    List<Spectator> getOffline();
}
