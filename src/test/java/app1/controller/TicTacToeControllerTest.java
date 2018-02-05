/*
package app1.controller;

import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class TicTacToeControllerTest {

    @Mock
    private BoardService boardService;

    @Mock
    private GameService gameService;

    @InjectMocks
    private TicTacToeController controller;

    private  MoveRequest moveRequest;
    private GameEntity gameEntity;
    private  Move move;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        moveRequest = new MoveRequest();
        gameEntity = new GameEntity();
        move = new Move();
        move.setSymbol('X');
    }
    @Test
    public void shouldCreateNewGameWhenNoGameExistingAndReturnTicTacToeView() throws Exception {
        when(gameService.loadGameByCurrentUser()).thenReturn(null);
        assertEquals("tictactoe", controller.tictactoeView());
        verify(gameService).createNewGame(anyString());
    }
    @Test
    public void shouldReturnTicTacToeViewWhenGameExists() throws Exception {
        when(gameService.loadGameByCurrentUser()).thenReturn(new GameEntity());
        assertEquals("tictactoe", controller.tictactoeView());
    }
    @Test
    public void shouldReturnListOfBoardResponse() {

        GameEntity gameEntity = new GameEntity();
        char[][] testBoard =  new char[3][3];
        testBoard[0][0] = 'C';
        testBoard[1][1] = 'C';

        when(gameService.loadGameByCurrentUser()).thenReturn(gameEntity);
        when(boardService.prepareAndPopulateBoard(gameEntity)).thenReturn(testBoard);

        assertEquals(2, controller.sendPopulatedBoard().size());
        assertThat(controller.sendPopulatedBoard().get(0), instanceOf(BoardResponse.class));

        testBoard[0][0] = '\u0000';
        testBoard[1][1] = '\u0000';
        assertEquals(0, controller.sendPopulatedBoard().size());
    }
    @Test
    public void shouldReturnStatusTakenAndUserSymbol() {

        when(gameService.loadGameByCurrentUser()).thenReturn(gameEntity);
        when(boardService.createMove(moveRequest,gameEntity)).thenReturn(move);
        when(boardService.isBoardCellAvailable(move)).thenReturn(false);

        assertEquals(GameStatus.TAKEN, controller.playerMove(moveRequest).getStatus());
        assertEquals('X', controller.playerMove(moveRequest).getSymbol());
    }
    @Test
    public void shouldReturnStatusWinAndRemoveGame() {

        when(gameService.loadGameByCurrentUser()).thenReturn(gameEntity);
        when(boardService.createMove(moveRequest,gameEntity)).thenReturn(move);
        when(boardService.isBoardCellAvailable(move)).thenReturn(true);
        when(boardService.checkGameStatus(gameEntity)).thenReturn(GameStatus.WIN);

        MovePlayerResponse  response = controller.playerMove(moveRequest);
        assertEquals(GameStatus.WIN, response.getStatus());
        assertEquals('X', response.getSymbol());
        verify(boardService).saveNewMove(move);
        verify(boardService).removeGame(gameEntity);
    }
    @Test
    public void shouldReturnStatusContinueAndChangePlayer() {

        when(gameService.loadGameByCurrentUser()).thenReturn(gameEntity);
        when(boardService.createMove(moveRequest,gameEntity)).thenReturn(move);
        when(boardService.isBoardCellAvailable(move)).thenReturn(true);
        when(boardService.checkGameStatus(gameEntity)).thenReturn(GameStatus.CONTINUE);

        MovePlayerResponse  response = controller.playerMove(moveRequest);
        assertEquals(GameStatus.CONTINUE, response.getStatus());
        assertEquals('X', response.getSymbol());
        verify(boardService).saveNewMove(move);
        verify(boardService).changePlayer(gameEntity);
    }
    @Test
    public void shouldReturnComputerMove() {

        Move computerMove = new Move();
        computerMove.setSymbol('O');
        when(gameService.loadGameByCurrentUser()).thenReturn(gameEntity);
        when(boardService.makeComputerMove(gameEntity)).thenReturn(computerMove);
        when(boardService.checkGameStatus(gameEntity)).thenReturn(GameStatus.CONTINUE);

        MoveComputerResponse  response = controller.computerMove();
        assertEquals(GameStatus.CONTINUE, response.getStatus());
        assertEquals('O', response.getSymbol());
        verify(boardService).saveNewMove(computerMove);
        verify(boardService).changePlayer(gameEntity);
    }

}*/
