package net.nekomine.spigot.state;

public interface StateService {

    /**
     * Получить текущее состояние
     *
     * @return текущее состояние
     */
    State getCurrentState();

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
