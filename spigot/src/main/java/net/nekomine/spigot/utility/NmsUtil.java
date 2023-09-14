package net.nekomine.spigot.utility;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

@UtilityClass
@SuppressWarnings("unused")
public class NmsUtil {

    private static final String CRAFTBUKKIT;
    private static final String NMS;
    private static final String VERSION;

    static {
        VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];
        CRAFTBUKKIT = "org.bukkit.craftbukkit." + VERSION;
        NMS = "net.minecraft.server." + VERSION;
    }

    public static @NotNull String getCraftBukkit() {
        return CRAFTBUKKIT;
    }

    public @NotNull String getServerVersion() {
        return VERSION;
    }

    @SneakyThrows
    public Class<?> getNMS(final String name) {
        return Class.forName(NMS + "." + name);
    }

    @SneakyThrows
    public Class<?> getCraftBukkit(final String name) {
        return Class.forName(CRAFTBUKKIT + "." + name);
    }
}
