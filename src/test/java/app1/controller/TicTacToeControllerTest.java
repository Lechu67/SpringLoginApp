package app1.controller;

import app1.computerMoveStrategy.Difficulty;
import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
public class TicTacToeControllerTest {

    @Mock
    private BoardService boardService;

    @Mock
    private GameService gameService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;
    @Mock
    private Board boardMock;

    @InjectMocks
    private TicTacToeController controller;

    private  MoveRequest moveRequest;
    private GameEntity gameEntity;
    private  Move move;
    @Mock
    private UserEntity userEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockStatic(SecurityContextHolder.class);
        moveRequest = new MoveRequest();
        gameEntity = new GameEntity();
        move = new Move();
        move.setSymbol('X');
        currentMockUser();
    }
    @Test
    public void shouldCreateNewGameWhenNoGameExistingAndReturnTicTacToeView() throws Exception {
        when(gameService.loadGameByCurrentUser(userEntity)).thenReturn(null);
        assertEquals("tictactoe", controller.tictactoeView());
        verify(gameService).createNewGame(anyChar(), any(UserEntity.class), eq(Difficulty.EASY));
    }
    @Test
    public void shouldReturnTicTacToeViewWhenGameExists() throws Exception {
        when(gameService.loadGameByCurrentUser(userEntity)).thenReturn(new GameEntity());
        assertEquals("tictactoe", controller.tictactoeView());
    }

    private void currentMockUser() {
        PowerMockito.when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEntity);
    }

    @Test
    public void shouldReturnListOfBoardResponse() {
        char[][] testBoard =  new char[3][3];
        testBoard[0][0] = 'C';
        testBoard[1][1] = 'C';

        when(boardService.getActualBoard(any())).thenReturn(boardMock);
        when(boardMock.getBoard()).thenReturn(testBoard);

        assertEquals(2, controller.sendPopulatedBoard().size());
        assertThat(controller.sendPopulatedBoard().get(0), instanceOf(BoardResponse.class));

        testBoard[0][0] = '\u0000';
        testBoard[1][1] = '\u0000';
        assertEquals(0, controller.sendPopulatedBoard().size());
    }
    @Test
    public void shouldReturnStatusTakenAndUserSymbol() {

        when(boardService.createMove(eq(moveRequest), any())).thenReturn(move);
        when(boardService.isBoardCellAvailable(move)).thenReturn(false);
        assertEquals(GameStatus.TAKEN, controller.playerMove(moveRequest).getStatus());
        assertEquals('X', controller.playerMove(moveRequest).getSymbol());
    }
    @Test
    public void shouldReturnStatusWinAndRemoveGame() {
        when(boardService.createMove(eq(moveRequest), any())).thenReturn(move);
        when(boardService.isBoardCellAvailable(move)).thenReturn(true);
        when(boardService.checkGameStatus(any())).thenReturn(GameStatus.WIN);

        MovePlayerResponse  response = controller.playerMove(moveRequest);
        assertEquals(GameStatus.WIN, response.getStatus());
        assertEquals('X', response.getSymbol());
        verify(boardService).saveNewMove(move);
        verify(boardService).removeGame(any());
    }
    @Test
    public void shouldReturnStatusContinueAndChangePlayer() {
        when(boardService.createMove(eq(moveRequest), any())).thenReturn(move);
        when(boardService.isBoardCellAvailable(move)).thenReturn(true);
        when(boardService.checkGameStatus(any())).thenReturn(GameStatus.CONTINUE);

        MovePlayerResponse  response = controller.playerMove(moveRequest);
        assertEquals(GameStatus.CONTINUE, response.getStatus());
        assertEquals('X', response.getSymbol());
        verify(boardService).saveNewMove(move);
        verify(boardService).changePlayer(any());
    }
    @Test
    public void shouldReturnComputerMove() {
        Move computerMove = new Move();
        computerMove.setSymbol('O');
        when(boardService.makeComputerMove(any())).thenReturn(computerMove);
        when(boardService.checkGameStatus(any())).thenReturn(GameStatus.CONTINUE);

        MoveComputerResponse  response = controller.computerMove();
        assertEquals(GameStatus.CONTINUE, response.getStatus());
        assertEquals('O', response.getSymbol());
        verify(boardService).saveNewMove(computerMove);
        verify(boardService).changePlayer(any());
    }

}
