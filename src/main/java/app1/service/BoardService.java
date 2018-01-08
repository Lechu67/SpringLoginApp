package app1.service;

import app1.model.Move;
import app1.model.MoveRequest;
import app1.repository.GameDAO;

//@Service
public class BoardService {

    //@Autowired
    private GameDAO gameDAO;

    public boolean checkIfBoardCellAvailable(MoveRequest moveRequest){

        Move move = new Move();
        move.setColumn(moveRequest.getX());
        move.setRow(moveRequest.getY());
        move.getGameID();// ??
        move.setUserSymbol('x');

        return gameDAO.isMoveExists(move);
    }
    public void makeNewMove(Move move){

    }

}
