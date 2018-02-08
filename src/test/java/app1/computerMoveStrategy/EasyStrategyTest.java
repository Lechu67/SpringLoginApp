package app1.computerMoveStrategy;

import app1.model.Board;
import app1.model.GameEntity;
import app1.model.Move;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class EasyStrategyTest {

    public EasyStrategy easyStrategy;
    public GameEntity gameEntity;

    @Before
    public void setUp() throws Exception {
        easyStrategy = new EasyStrategy();
        gameEntity = new GameEntity();
        gameEntity.setComputerSymbol('O');
    }
    @Test
    public void shouldMakeComputerMoveOnCoord1_0() {
        Move computerMove = easyStrategy.getComputerMove(getTestBoard());
        assertThat(computerMove.getColumn(), is(1));
        assertThat(computerMove.getRow(), is(0));
        assertThat(computerMove.getSymbol(), is('O'));
    }

    @Test
    public void shouldReturnComputerMoveAsNullBecauseOfFullBoard() {
        Move move = easyStrategy.getComputerMove(getDrawTestBoard());
        assertEquals(null, move);
    }

    public Board getTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 0, gameEntity, 'O'));
        moves.add(new Move(1, 2, gameEntity, 'X'));
        return new Board(gameEntity, moves);
    }
    private Board getDrawTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 0, gameEntity, 'X'));
        moves.add(new Move(0, 1, gameEntity, 'O'));
        moves.add(new Move(0, 2, gameEntity, 'X'));
        moves.add(new Move(1, 0, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'O'));
        moves.add(new Move(1, 2, gameEntity, 'O'));
        moves.add(new Move(2, 0, gameEntity, 'O'));
        moves.add(new Move(2, 1, gameEntity, 'X'));
        moves.add(new Move(2, 2, gameEntity, 'X'));
        return new Board(gameEntity, moves);
    }
}
