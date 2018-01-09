package app1.service;

import app1.model.GameEntity;
import app1.model.GameStatus;
import app1.model.Move;
import app1.repository.GameDAO;

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
        checkIfWin(board);

        return null;
    }

    private boolean checkIfWin(char[][] board) {



        return false;
    }

    private char[][] prepareAndPopulateBoard(int gameId, int boardSize){

        char[][] board = new char[boardSize][boardSize];
        List<Move> moves = gameDAO.findMovesByGameId(gameId);
        for(Move m : moves){
            int x = m.getColumn();
            int y = m.getRow();
            board[x][y] = m.getSymbol();
        }
        return board;
    }

}
