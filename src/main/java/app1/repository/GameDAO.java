package app1.repository;

import app1.model.GameEntity;
import app1.model.Move;

public interface GameDAO {

    boolean isMoveExists(Move move);

    void saveNewGame(GameEntity gameEntity);
}
