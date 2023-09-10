package net.nekomine.spigot;


import net.nekomine.spigot.state.State;
import net.nekomine.spigot.state.StateService;
import net.nekomine.spigot.state.exception.StateException;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

class StateServiceImpl implements StateService {
    private final Queue<State> stateQueue = new ArrayDeque<>();

    @Override
    public void addState(State... states) {
        stateQueue.addAll(Arrays.asList(states));
    }

    @Override
    public void nextState() {
        State oldState = stateQueue.peek();

        if (oldState == null) {
            throw new StateException("Старое состояние не найдено!");
        }

        oldState.end();

        State newState = stateQueue.poll();

        if (newState == null) {
            throw new StateException("Новое состояние не найдено!");
        }

        newState.start();
    }

}
