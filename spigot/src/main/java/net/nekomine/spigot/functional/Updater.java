package net.nekomine.spigot.functional;

@FunctionalInterface
public interface Updater<T> {
    void update(T t);

}
