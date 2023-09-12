package net.nekomine.spigot.tag;

import net.kyori.adventure.text.Component;
import net.nekomine.spigot.functional.Deleter;
import net.nekomine.spigot.functional.Sender;

public interface Tag extends Sender, Deleter {

    Component getPrefix();

    Component getSuffix();

    Component getName();

    void setPrefix(Component component);

    void setSuffix(Component component);

    void setName(Component component);

}
