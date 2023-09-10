package net.nekomine.spigot.state;

public interface StateService {

    /**
     * Добавить состояния в очередь
     *
     * @param states состояния
     */
    void addState(State... states);

    /**
     * Сменить состояние
     *
     * @throws net.nekomine.spigot.state.exception.StateException при ошибке смены состояния
     */
    void nextState();

}
