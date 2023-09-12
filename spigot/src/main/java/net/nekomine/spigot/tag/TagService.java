package net.nekomine.spigot.tag;

import net.nekomine.spigot.functional.Updater;

public interface TagService {

    Tag createTag(Updater<Tag> updater);

}
