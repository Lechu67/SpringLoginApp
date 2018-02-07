package app1.computerMoveStrategy;

import app1.model.Board;
import app1.model.Move;

public interface ComputerMoveStrategy {

    Move getComputerMove(Board board);
}
