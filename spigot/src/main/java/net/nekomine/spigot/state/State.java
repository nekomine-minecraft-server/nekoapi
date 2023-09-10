package net.nekomine.spigot.state;

public interface State {

    /**
     * Вызывается в начале состояния
     */
    void start();

    /**
     * Вызывается в конце состояния
     */
    void end();

}
