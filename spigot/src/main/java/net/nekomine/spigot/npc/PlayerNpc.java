package net.nekomine.spigot.npc;

import net.nekomine.common.record.Skin;

public interface PlayerNpc extends Npc {

    Skin getSkin();

    void setSkin(Skin skin);
}
