package app1.repository;

import app1.model.GameEntity;
import app1.model.Move;

import java.util.List;

public interface GameDAO {

    boolean isMoveExists(Move move);

    List<Move> findMovesByGameId(int gameId);

    void saveNewMove(Move move);

    void saveNewGame(GameEntity gameEntity);

    GameEntity findGameByUserName(String userName);
}
