package app1.service;

import app1.model.GameEntity;
import app1.model.GameStatus;
import app1.model.Move;
import app1.repository.GameDAO;
import app1.winStrategy.DiagonalWinStrategy;
import app1.winStrategy.HorizontalWinStrategy;
import app1.winStrategy.VerticalWinStrategy;
import app1.winStrategy.WinStrategy;

import java.util.ArrayList;
import java.util.List;

//@Service
public class BoardService {

    //@Autowired
    private GameDAO gameDAO;

    public boolean checkIfBoardCellAvailable(Move move){
        return gameDAO.isMoveExists(move);
    }

    public void saveNewMove(Move move){
        gameDAO.saveNewMove(move);
    }


    //maybe pass size as param ?
    public GameStatus checkGameStatus(GameEntity gameEntity){

        int boardSize = 3;
        char[][] board = prepareAndPopulateBoard(gameEntity.getId(), boardSize);
        if(checkIfWin(board)){
            return GameStatus.WIN; // who wins ???
        } else if(checkIfDraw(board)){
            return GameStatus.DRAW;
        }else{
            return GameStatus.CONTINUE;
        }
    }

    private boolean checkIfDraw(char[][] board) {
        return false;
    }


    private boolean checkIfWin(char[][] board) {

        List<WinStrategy> winStrategies = new ArrayList<>();
        winStrategies.add(new HorizontalWinStrategy());
        winStrategies.add(new VerticalWinStrategy());
        winStrategies.add(new DiagonalWinStrategy());

        return winStrategies.stream().anyMatch(winStrategy -> winStrategy.isWin(board));
    }

    private char[][] prepareAndPopulateBoard(int gameId, int boardSize){

        char[][] board = new char[boardSize][boardSize];
        List<Move> moves = gameDAO.findMovesByGameId(gameId);
        moves.forEach(m -> {
            int x = m.getColumn();
            int y = m.getRow();
            board[x][y] = m.getSymbol();
        });
        return board;
    }

}
