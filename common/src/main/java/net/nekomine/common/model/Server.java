package net.nekomine.common.model;

import java.util.List;

@SuppressWarnings("unused")
public interface Server extends BaseModel<String> {
    String getName();

    int getOnline();

    int getMaxPlayers();

    List<String> getAllPlayers();

}
