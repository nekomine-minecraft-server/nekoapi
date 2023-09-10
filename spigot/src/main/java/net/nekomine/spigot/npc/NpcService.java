package net.nekomine.spigot.npc;


import net.nekomine.common.record.Skin;

public interface NpcService {

    PlayerNpc createPlayer();

    PlayerNpc createPlayer(Skin skin);

}
