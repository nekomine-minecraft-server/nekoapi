package net.nekomine.spigot.utility.functional;

@FunctionalInterface
public interface Updater<T> {
    void update(T t);

}
