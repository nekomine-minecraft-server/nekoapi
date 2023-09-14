package net.nekomine.spigot.state;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import java.util.Collections;
import java.util.List;

public interface State {

    /**
     * Вызывается в начале состояния
     */
    void start();

    /**
     * Вызывается в конце состояния
     */
    void end();

    /**
     * Список ников игроков
     */
    default List<String> getGamers() {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).toList();
    }

    /**
     * Список наблюдателей
     */
    default List<String> getSpectators() {
        return Collections.emptyList();
    }

    /**
     * Ждёт ли состояние игроков (если ждём, то игроки могут свободно заходить)
     */
    default boolean waitGamers() {
        return true;
    }

    /**
     * Ждём ли состояние наблюдателей (если ждём, то наблюдатели могут заходить на сервер)
     */
    default boolean waitSpectators() {
        return !waitGamers();
    }

    /**
     * Имя карты, используется в селекторах
     */
    default String getMapName() {
        return "world";
    }

}
