package net.nekomine.spigot.board;

import net.nekomine.spigot.functional.Updater;

public interface BoardService {
    /**
     * Create component board
     *
     * @param updater on update action
     * @return new component board
     */
    Board createBoard(Updater<Board> updater);

}
