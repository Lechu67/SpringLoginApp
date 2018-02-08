package app1.service;

import app1.computerMoveStrategy.ComputerMoveStrategy;
import app1.computerMoveStrategy.Difficulty;
import app1.computerMoveStrategy.EasyStrategy;
import app1.model.*;
import app1.repository.GameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BoardService {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private Map<Difficulty,ComputerMoveStrategy> computerMoveStrategyMap;



    public boolean isBoardCellAvailable(Move move){
        return gameDAO.isMovePossible(move);
    }

    public void saveNewMove(Move move){
        gameDAO.saveNewMove(move);
    }
    //TODO
    public void changePlayer(GameEntity gameEntity){
        char playerSymbol = gameEntity.getUserSymbol();
        char computerSymbol = gameEntity.getComputerSymbol();
        if (gameEntity.getCurrentPlayingSymbol() == playerSymbol) {
            gameEntity.setCurrentPlayingSymbol(computerSymbol);
        } else {
            gameEntity.setCurrentPlayingSymbol(playerSymbol);
        }
        gameDAO.updateGame(gameEntity);
    }
    public Move makeComputerMove(GameEntity gameEntity){
        Difficulty difficulty = Difficulty.valueOf(gameEntity.getDifficulty());
        ComputerMoveStrategy computerMoveStrategy = computerMoveStrategyMap.get(difficulty);
        return computerMoveStrategy.getComputerMove(getActualBoard(gameEntity));
    }
    public Board getActualBoard(GameEntity gameEntity){
        return new Board(gameEntity,gameDAO.findMovesByGame(gameEntity));
    }

    public GameStatus checkGameStatus(GameEntity gameEntity){
        return getActualBoard(gameEntity).checkGameStatus();
    }

    public void removeGame(GameEntity currentGameEntity) {
        gameDAO.removeGameAndMoves(currentGameEntity);
    }

    public Move createMove(MoveRequest moveRequest, GameEntity gameEntity) {
        return new Move(
                moveRequest.getX(),
                moveRequest.getY(),
                gameEntity,
                gameEntity.getUserSymbol());
    }
}
