package app1.computerMoveStrategy;

import app1.model.Board;
import app1.model.Move;
import org.springframework.stereotype.Component;

@Component
public class EasyStrategy implements ComputerMoveStrategy {

    public static final char EMPTY_FIELD = '\u0000';

    @Override
    public Move getComputerMove(Board board) {
        int x = 0;
        int y = 0;
        char [][] currentBoard = board.getBoard();
        for (int row = 0; row < currentBoard.length; row++) {
            for (int col = 0; col < currentBoard[row].length; col++) {
                if (currentBoard[col][row] == EMPTY_FIELD) {
                    x = col;
                    y = row;
                    return new Move(x,y,board.getGameEntity(),'O');
                }
            }
        }
        return null;
    }
}
