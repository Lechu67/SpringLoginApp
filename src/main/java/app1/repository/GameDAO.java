package app1.repository;

import app1.model.GameEntity;
import app1.model.Move;
import app1.model.UserEntity;

import java.util.List;

public interface GameDAO {

    boolean isMovePossible(Move move);

    List<Move> findMovesByGame(GameEntity gameEntity);

    void saveNewMove(Move move);

    void updateGame(GameEntity gameEntity);

    void saveNewGame(GameEntity gameEntity);

    GameEntity findGameByUserName(UserEntity userEntity);

    void removeGameAndMoves(GameEntity currentGameEntity);
}
