package app1.service;

import app1.model.GameEntity;
import app1.model.GameStatus;
import app1.model.Move;
import app1.repository.GameDAO;
import app1.winStrategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    }
    public Move makeComputerMove(GameEntity gameEntity){

        int x = 0;
        int y = 0;
        char[][] board = prepareAndPopulateBoard(gameEntity);
        for (int row = 0 ; row < board.length ; row++) {
            for (int col = 1; col < board[row].length; col++) {
                if (board[row][col] == 0) {
                    x = col;
                    y = row;
                }
            }
        }
        Move computerMove = new Move(
                x,
                y,
                gameEntity.getId(),
                'O');
        return computerMove;
    }


    //maybe pass size as param ?
    public GameStatus checkGameStatus(GameEntity gameEntity){

        char[][] board = prepareAndPopulateBoard(gameEntity);
        if(tryGetWinner(board) != null){
            return GameStatus.WIN; // who wins ???
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
        winStrategies.add(new HorizontalWinStrategy());
        winStrategies.add(new VerticalWinStrategy());
        winStrategies.add(new DiagonalWinStrategy());
        winStrategies.add(new AntiDiagonalWinStrategy());

        Character winner = null;
        for (WinStrategy winStrategy : winStrategies){
            winner = winStrategy.isWin(board);
        }
        return winner;
    }
    private char[][] prepareAndPopulateBoard(GameEntity gameEntity){

        char[][] board = new char[gameEntity.getDimension()][gameEntity.getDimension()];
        List<Move> moves = gameDAO.findMovesByGameId(gameEntity.getId());
        moves.forEach(m -> {
            int x = m.getColumn();
            int y = m.getRow();
            board[x][y] = m.getSymbol();
        });
        return board;
    }

}
