package net.nekocraft.spigot.impl.gameuser;

import com.destroystokyo.paper.event.entity.EntityPathfindEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import net.kyori.adventure.text.Component;
import net.nekomine.spigot.gameuser.Spectator;
import net.nekomine.spigot.gameuser.SpectatorService;
import net.nekomine.spigot.state.SpectatorState;
import net.nekomine.spigot.state.State;
import net.nekomine.spigot.state.StateService;
import net.nekomine.spigot.utility.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.List;

public class SpectatorListener implements Listener {
    private final SpectatorService spectatorService;
    private final SpectatorTasks spectatorTasks;
    private final StateService stateService;

    public SpectatorListener(SpectatorService spectatorService, SpectatorTasks spectatorTasks, StateService stateService) {
        this.spectatorService = spectatorService;
        this.spectatorTasks = spectatorTasks;
        this.stateService = stateService;
    }

    @EventHandler
    public void onHeldSlot(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItem(e.getPreviousSlot());
        if (item == null || item.getType() != Material.COMPASS) {
            return;
        }

        player.sendActionBar(Component.text(" "));
    }

    @EventHandler(ignoreCancelled = true)
    public void spectatorStandBack(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (isSpectator(player) || !hasBlock(player)) {
            return;
        }
        List<Player> spectators = LocationUtil.getNearPlayers(player, 2);
        if (spectators.isEmpty()) {
            return;
        }
        for (Player spectator : spectators) {
            if (isSpectator(spectator)) {
                Vector vector = spectator.getLocation().toVector().subtract(player.getLocation().toVector());
                vector.setY(0.8);
                spectator.setVelocity(vector.normalize().multiply(1.1));
            }
        }
    }

    private boolean hasBlock(Player player) {
        ItemStack itemOnCursor = player.getItemOnCursor();
        PlayerInventory inventory = player.getInventory();
        ItemStack itemInOffHand = inventory.getItemInOffHand();
        ItemStack itemInMainHand = inventory.getItemInMainHand();

        if (itemOnCursor.getType().isSolid()) {
            return true;
        }

        if (itemInOffHand.getType().isSolid()) {
            return true;
        }

        return itemInMainHand.getType().isSolid();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onArmorStand(PlayerArmorStandManipulateEvent e) {
        if (isSpectator(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        //event.setSpawnLocation(game.getSetting(GameSettings.LOBBY_LOCATION));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        if (event.getClick() != ClickType.NUMBER_KEY) {
            return;
        }

        if (isSpectator(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Entity vehicle = player.getVehicle();

        if (vehicle != null) {
            vehicle.removePassenger(player);
        }

        spectatorTasks.cancelAllTasks(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHandChange(PlayerSwapHandItemsEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPotionSplash(PotionSplashEvent event) {
        for (LivingEntity livingEntity : event.getAffectedEntities()) {
            if (livingEntity.getType() != EntityType.PLAYER) {
                continue;
            }

            if (isSpectator((Player) livingEntity)) {
                event.setIntensity(livingEntity, 0);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPotionSplash(AreaEffectCloudApplyEvent event) {
        event.getAffectedEntities().removeIf(livingEntity -> {
            if (livingEntity instanceof Player player) {
                return isSpectator(player);
            }

            return false;
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTryBuild(BlockCanBuildEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isSpectator(player)) {
                continue;
            }

            if (player.getWorld() == location.getWorld() && player.getLocation().distance(location) <= 2) {
                if (block.getType() == Material.AIR) {
                    event.setBuildable(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickup(EntityPickupItemEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (isSpectator((Player) entity)) {
            event.setCancelled(true);
        }
    }

    private boolean isSpectator(Player player) {
        return spectatorService.isSpectator(player.getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickup(PlayerPickupArrowEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityTarget(EntityTargetEvent e) {
        Entity entity = e.getTarget();
        if (entity == null || entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (isSpectator((Player) entity)) {
            e.setTarget(null);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFood(FoodLevelChangeEvent event) {
        HumanEntity entity = event.getEntity();
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }
        if (isSpectator((Player) entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!isSpectator(player)) {
            return;
        }

        Spectator spectator = spectatorService.getSpectator(player.getName()).orElseThrow();

        if (event.getTo().getY() <= 0) {
            State state = stateService.getCurrentState();

            if (state instanceof SpectatorState spectatorState) {
                player.teleport(spectatorState.getSpectatorLocation());
            }
        }

        if (spectator.isAlwaysFly()) {
            player.setFlying(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onProjectile(ProjectileHitEvent e) { //запретить удочкой по спектру
        Projectile projectile = e.getEntity();
        Entity entity = e.getHitEntity();
        if (entity == null || entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (entity instanceof Player player) {
            if (isSpectator(player)) {
                projectile.remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFish(PlayerFishEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (isSpectator(player)) {
            switch (event.getCause()) {
                case FIRE, FIRE_TICK, LAVA, LIGHTNING ->
                        player.setFireTicks(0);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (damager.getType() != EntityType.PLAYER) {
            return;
        }

        if (isSpectator((Player) damager)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVehicleDestroy(VehicleDamageEvent event) {
        if (!(event.getAttacker() instanceof Player)) {
            return;
        }

        if (isSpectator((Player) event.getAttacker())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVehicleDestroy(VehicleEntityCollisionEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }

        if (isSpectator((Player) event.getEntity())) {
            event.setCancelled(true);
            event.setPickupCancelled(true);
            event.setCollisionCancelled(true);
        }
    }

    @EventHandler
    public void onTarget(EntityPathfindEvent e) {
        Entity targetEntity = e.getTargetEntity();
        if (targetEntity == null || targetEntity.getType() != EntityType.PLAYER) {
            return;
        }

        if (isSpectator((Player) targetEntity)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onOrb(PlayerPickupExperienceEvent e) {
        if (isSpectator(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
