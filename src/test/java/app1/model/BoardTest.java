package app1.model;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BoardTest {


    public Board boardTestClass;
    public GameEntity gameEntity;
    public List<Move> moves;

    @Before
    public void setup(){
        gameEntity = new GameEntity();
        moves = new ArrayList<>();
        boardTestClass = new Board(gameEntity, moves);
    }
    @Test
    public void shouldPrepareAndPopulateBoard(){
        char[][] board = getTestBoard();
        assertThat(board[0][0], is('O'));
        assertThat(board[1][2], is('X'));
        assertThat(board[1][0], is('\u0000'));
    }
    @Test
    public void shouldReturnDrawStatus(){
        char[][] board = getDrawTestBoard();
        GameStatus gameStatus = boardTestClass.checkGameStatus();
        assertThat(gameStatus, is(GameStatus.DRAW));
    }
    @Test
    public void shouldReturnVerticalWinStatus(){
        char[][] board = getVerticalTestBoard();
        GameStatus gameStatus = boardTestClass.checkGameStatus();
        assertThat(gameStatus, is(GameStatus.WIN));
    }
    @Test
    public void shouldReturnHorizontalWinStatus(){
        char[][] board = getHorizontalTestBoard();
        GameStatus gameStatus = boardTestClass.checkGameStatus();
        assertThat(gameStatus, is(GameStatus.WIN));
    }
    @Test
    public void shouldReturnDiagonalWinStatus(){
        char[][] board = getDiagonalTestBoard();
        GameStatus gameStatus = boardTestClass.checkGameStatus();
        assertThat(gameStatus, is(GameStatus.WIN));
    }
    @Test
    public void shouldReturnAntiDiagonalWinStatus(){
        char[][] board = getAntiDiagonalTestBoard();
        GameStatus gameStatus = boardTestClass.checkGameStatus();
        assertThat(gameStatus, is(GameStatus.WIN));
    }

    public char[][] getTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 0, gameEntity, 'O'));
        moves.add(new Move(1, 2, gameEntity, 'X'));
        return boardTestClass.prepareAndPopulateBoard(moves);
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
        return boardTestClass.prepareAndPopulateBoard(moves);
    }
    private char[][] getVerticalTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(1, 0, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'X'));
        moves.add(new Move(1, 2, gameEntity, 'X'));
        return boardTestClass.prepareAndPopulateBoard(moves);
    }
    private char[][] getHorizontalTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 1, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'X'));
        moves.add(new Move(2, 1, gameEntity, 'X'));
        return boardTestClass.prepareAndPopulateBoard(moves);
    }
    private char[][] getDiagonalTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 0, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'X'));
        moves.add(new Move(2, 2, gameEntity, 'X'));
        return boardTestClass.prepareAndPopulateBoard(moves);
    }
    private char[][] getAntiDiagonalTestBoard() {
        gameEntity.setDimension(3);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 2, gameEntity, 'X'));
        moves.add(new Move(1, 1, gameEntity, 'X'));
        moves.add(new Move(2, 0, gameEntity, 'X'));
        return boardTestClass.prepareAndPopulateBoard(moves);
    }

}