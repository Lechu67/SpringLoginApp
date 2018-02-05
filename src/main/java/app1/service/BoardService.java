package app1.service;

import app1.model.*;
import app1.repository.GameDAO;
import app1.winStrategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class BoardService {

    public static final char EMPTY_FIELD = '\u0000';
    @Autowired
    private GameDAO gameDAO;

    public boolean isBoardCellAvailable(Move move){
        return gameDAO.isMovePossible(move);
    }

    public void saveNewMove(Move move){
        gameDAO.saveNewMove(move);
    }

    public void changePlayer(GameEntity gameEntity){
        gameEntity.setUserNextMove(!gameEntity.isUserNextMove());
        gameDAO.updateGame(gameEntity);
    }
    public Move makeComputerMove(/*ComputerMoveStrategy strategy, */GameEntity gameEntity){
        int x = 0;
        int y = 0;
        char[][] board = getActualBoard(gameEntity).getBoard();

        // Move move = strategy.move(board);
        // validate move;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[col][row] == EMPTY_FIELD) {
                    x = col;
                    y = row;
                    return new Move(x,y,gameEntity,'O');
                }
            }
        }
        return null;
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
