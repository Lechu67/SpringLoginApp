/*
package app1.service;

import app1.model.GameEntity;
import app1.model.GameStatus;
import app1.model.Move;
import app1.repository.GameDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private GameDAO gameDAO;

    private Move move;
    private GameEntity gameEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        move = new Move();
        gameEntity = new GameEntity();
    }


    @Test // czy taki test ma sens ?
    public void shouldCallDAOMethod() {
        boardService.isBoardCellAvailable(move);
        verify(gameDAO).isMovePossible(move);
    }
    @Test
    public void shouldChangePlayerAndUpdateGame() {

        gameEntity.setUserNextMove(true);
        boardService.changePlayer(gameEntity);
        assertThat(gameEntity.isUserNextMove(), is(false));
        verify(gameDAO).updateGame(gameEntity);
    }
    @Test
    public void shouldPrepareBoardWithSymbols() {
        char[][] board = getTestBoard();
        assertThat(board[0][0], is('O'));
        assertThat(board[1][2], is('X'));
        assertThat(board[1][0], is('\u0000'));
    }
    @Test
    public void shouldMakeComputerMoveOnCoord1_0() {
        char[][] board = getTestBoard();
        Move computerMove = boardService.makeComputerMove(gameEntity);
        assertThat(computerMove.getColumn(), is(1));
        assertThat(computerMove.getRow(), is(0));
        assertThat(computerMove.getSymbol(), is('O'));
    }
    @Test
    public void shouldReturnComputerMoveAsNullBecauseOfFullBoard() {
        char[][] board = getDrawTestBoard();
        Move move = boardService.makeComputerMove(gameEntity);
        assertEquals(null, move);
    }
    @Test
    public void shouldReturnDrawStatus(){
        char[][] board = getDrawTestBoard();
        GameStatus gameStatus = boardService.checkGameStatus(gameEntity);
        assertThat(gameStatus, is(GameStatus.DRAW));
    }
    @Test
    public void shouldReturnHorizontalWinStatus(){
        char[][] board = getHorizontalTestBoard();
        GameStatus gameStatus = boardService.checkGameStatus(gameEntity);
        assertThat(gameStatus, is(GameStatus.WIN));
    }
    @Test
    public void shouldReturnVerticalWinStatus(){
        char[][] board = getVerticalTestBoard();
        GameStatus gameStatus = boardService.checkGameStatus(gameEntity);
        assertThat(gameStatus, is(GameStatus.WIN));
    }
    @Test
    public void shouldReturnDiagonalWinStatus(){
        char[][] board = getDiagonalTestBoard();
        GameStatus gameStatus = boardService.checkGameStatus(gameEntity);
        assertThat(gameStatus, is(GameStatus.WIN));
    }
    @Test
    public void shouldReturnAntiDiagonalWinStatus(){
        char[][] board = getAntiDiagonalTestBoard();
        GameStatus gameStatus = boardService.checkGameStatus(gameEntity);
        assertThat(gameStatus, is(GameStatus.WIN));
    }

    private char[][] getTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 0, gameEntity, 'O'));
        moves.add(new Move(1, 2, gameEntity, 'X'));
        when(gameDAO.findMovesByGame(gameEntity)).thenReturn(moves);
        return boardService.prepareAndPopulateBoard(gameEntity);
    }
    private char[][] getDrawTestBoard() {
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

        when(gameDAO.findMovesByGame(gameEntity)).thenReturn(moves);
        return boardService.prepareAndPopulateBoard(gameEntity);
    }
    private char[][] getVerticalTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(1, 0, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'X'));
        moves.add(new Move(1, 2, gameEntity, 'X'));
        when(gameDAO.findMovesByGame(gameEntity)).thenReturn(moves);
        return boardService.prepareAndPopulateBoard(gameEntity);
    }
    private char[][] getHorizontalTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 1, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'X'));
        moves.add(new Move(2, 1, gameEntity, 'X'));
        when(gameDAO.findMovesByGame(gameEntity)).thenReturn(moves);
        return boardService.prepareAndPopulateBoard(gameEntity);
    }
    private char[][] getDiagonalTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 0, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'X'));
        moves.add(new Move(2, 2, gameEntity, 'X'));
        when(gameDAO.findMovesByGame(gameEntity)).thenReturn(moves);
        return boardService.prepareAndPopulateBoard(gameEntity);
    }
    private char[][] getAntiDiagonalTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 2, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'X'));
        moves.add(new Move(2, 0, gameEntity, 'X'));
        when(gameDAO.findMovesByGame(gameEntity)).thenReturn(moves);
        return boardService.prepareAndPopulateBoard(gameEntity);
    }
}*/
