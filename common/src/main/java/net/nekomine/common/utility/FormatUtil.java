package net.nekomine.common.utility;

public class FormatUtil {

    /**
     * Форматировать строки по ключам
     *
     * @param string  строка
     * @param objects ключи, строки
     * @return отформатированное сообщение
     */
    public String formatByKeys(String string, Object... objects) {
        if (objects == null) {
            return string;
        }

        if (objects.length % 2 != 0) {
            return string;
        }

        for (int i = 0; i < objects.length; i = i + 2) {
            Object key = objects[i];

            if (key == null) {
                throw new IllegalStateException("Key can't be null");
            }

            Object value = objects[i + 1];

            string = string.replace(key.toString(), value == null ? "null" : value.toString());
        }

        return string;
    }
}