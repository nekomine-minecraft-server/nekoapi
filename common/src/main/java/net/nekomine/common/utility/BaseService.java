package net.nekomine.common.utility;

import lombok.Getter;

@Getter
public class BaseService implements Service {
    private boolean enabled = false;

    @Override
    public void enable() {
        if (isEnabled()) {
            throw new IllegalArgumentException("Сервис уже включён!");
        }

        enabled = true;
    }

    @Override
    public void disable() {
        if (!isEnabled()) {
            throw new IllegalArgumentException("Сервис уже выключен!");
        }

        enabled = false;
    }
}
