package net.nekocraft.spigot.impl.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Gravity;
import net.citizensnpcs.trait.SkinTrait;
import net.nekomine.common.record.Skin;
import net.nekomine.spigot.npc.PlayerNpc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class PlayerNpcImpl implements PlayerNpc {
    private final Plugin plugin;
    private final NPC npc;

    public PlayerNpcImpl(Plugin plugin) {
        this.plugin = plugin;
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "kek");
        this.npc.setUseMinecraftAI(false);
        this.npc.getOrAddTrait(Gravity.class).gravitate(false);
    }

    public PlayerNpcImpl(Plugin plugin, Skin skin) {
        this.plugin = plugin;
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "kek");
        this.npc.setUseMinecraftAI(false);
        this.npc.getOrAddTrait(Gravity.class).gravitate(false);
        this.npc.getOrAddTrait(SkinTrait.class).setTexture(skin.value(), skin.signature());
    }

    public void setGravity(boolean gravity) {
        npc.getOrAddTrait(Gravity.class).gravitate(gravity);
    }

    public boolean getGravity() {
        return npc.getOrAddTrait(Gravity.class).hasGravity();
    }

    @Override
    public Skin getSkin() {
        SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
        return new Skin(skinTrait.getTexture(), skinTrait.getSignature());
    }

    @Override
    public void setGlowing(boolean glowing) {
        npc.data().set("glowing", true);
    }

    @Override
    public boolean getGlowing() {
        return npc.data().get("glowing");
    }

    @Override
    public void setSkin(Skin skin) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            npc.data().set("player-skin-textures", skin.value());
            npc.data().set("player-skin-signature", skin.signature());
        }, 20 * 2L);
    }

    @Override
    public void spawn(Location location) {
        npc.spawn(location);
    }
}
