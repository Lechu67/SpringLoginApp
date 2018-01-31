package app1.service;

import app1.model.GameEntity;
import app1.model.GameStatus;
import app1.model.Move;
import app1.model.MoveRequest;
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
    public Move makeComputerMove(GameEntity gameEntity){
        int x = 0;
        int y = 0;
        char[][] board = prepareAndPopulateBoard(gameEntity);
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[col][row] == '\u0000') {
                    x = col;
                    y = row;
                    return new Move(x,y,gameEntity,'O');
                }
            }
        }
        return null;
    }
    public GameStatus checkGameStatus(GameEntity gameEntity){
        char[][] board = prepareAndPopulateBoard(gameEntity);
        if(tryGetWinner(board) != null){
            return GameStatus.WIN;
        } else if(checkIfDraw(board)){
            return GameStatus.DRAW;
        }else{
            return GameStatus.CONTINUE;
        }
    }
    private boolean checkIfDraw(char[][] board) {
        for (int row = 0 ; row < board.length ; row++){
            for (int col = 0 ; col < board[row].length ; col++){
                if(board[row][col] == 0){
                    return false;
                }
            }
        }
        return true;
    }
    private Character tryGetWinner(char[][] board) {
        List<WinStrategy> winStrategies = new ArrayList<>();
        winStrategies.add(new VerticalWinStrategy());
        winStrategies.add(new HorizontalWinStrategy());
        winStrategies.add(new DiagonalWinStrategy());
        winStrategies.add(new AntiDiagonalWinStrategy());

        Character winner = null;
        for (WinStrategy winStrategy : winStrategies){
            winner = winStrategy.isWin(board);
            if(winner != null){
                return winner;
            }
        }
        return null;
    }
    public char[][] prepareAndPopulateBoard(GameEntity gameEntity){

        char[][] board = new char[gameEntity.getDimension()][gameEntity.getDimension()];
        List<Move> moves = gameDAO.findMovesByGame(gameEntity);
        moves.forEach(m -> {
            int x = m.getColumn();
            int y = m.getRow();
            board[x][y] = m.getSymbol();
        });
        return board;
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
