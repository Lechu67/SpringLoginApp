//package app1.controller;
//
//import app1.computerMoveStrategy.Difficulty;
//import app1.model.*;
//import app1.service.BoardService;
//import app1.service.GameService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyChar;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(SecurityContextHolder.class)
//public class TicTacToeControllerTest {
//
//    @Mock
//    private BoardService boardService;
//
//    @Mock
//    private GameService gameService;
//
//    @Mock
//    private SecurityContext securityContext;
//
//    @Mock
//    private Authentication authentication;
//
//    @Mock
//    private UserEntity userEntity;
//
//    @InjectMocks
//    private TicTacToeController controller;
//
//    private  MoveRequest moveRequest;
//    private  Move move;
//    private GameEntity gameEntity;
//    private List<Move> moves;
//    private Board board;
//
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        mockStatic(SecurityContextHolder.class);
//        gameEntity = new GameEntity('X','O','O',userEntity,3,"EASY");
//        moves = new ArrayList<>();
//        moves.add(new Move(0,0, gameEntity,'X'));
//        moves.add(new Move(0,1, gameEntity,'O'));
//        move = moves.get(0);
//        board = new Board(gameEntity, moves);
//        moveRequest = new MoveRequest();
//        currentMockUser();
//        when(gameService.loadGameByUser(any())).thenReturn(gameEntity);
//    }
//    private void currentMockUser() {
//        PowerMockito.when(SecurityContextHolder.getContext()).thenReturn(securityContext);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(userEntity);
//    }
//    @Test
//    public void shouldCreateNewGameWhenNoGameExistingAndReturnTicTacToeView() throws Exception {
//        when(gameService.loadGameByUser(any())).thenReturn(null);
//        assertEquals("tictactoe", controller.tictactoeView());
//        verify(gameService).createNewGame(anyChar(),anyChar(), any(UserEntity.class), eq(Difficulty.EASY));
//    }
//    @Test
//    public void shouldReturnTicTacToeViewWhenGameExists() throws Exception {
//        when(gameService.loadGameByUser(userEntity)).thenReturn(new GameEntity());
//        assertEquals("tictactoe", controller.tictactoeView());
//    }
//
////    @Test
////    public void shouldReturnListOfBoardResponse() {
////        when(boardService.getActualBoard(any())).thenReturn(board);
////        assertEquals(2, controller.sendPopulatedBoard().getSymbolsLocations().size());
////    }
//    @Test
//    public void shouldReturnStatusTakenAndUserSymbol() {
//        when(boardService.createMove(eq(moveRequest), any())).thenReturn(move);
//        when(boardService.isBoardCellAvailable(move)).thenReturn(false);
//        gameEntity.setCurrentPlayingSymbol('X');
//        assertEquals(GameStatus.TAKEN, controller.playerMove(moveRequest).getStatus());
//        assertEquals('X', controller.playerMove(moveRequest).getSymbol());
//    }
//    @Test
//    public void shouldReturnStatusWinAndRemoveGame() {
//        when(boardService.createMove(eq(moveRequest), any())).thenReturn(move);
//        when(boardService.isBoardCellAvailable(move)).thenReturn(true);
//        when(boardService.checkGameStatus(any())).thenReturn(GameStatus.WIN);
//        gameEntity.setCurrentPlayingSymbol('X');
//
//        MovePlayerResponse response = controller.playerMove(moveRequest);
//        assertEquals(GameStatus.WIN, response.getStatus());
//        assertEquals('X', response.getSymbol());
//        verify(boardService).saveNewMove(move);
//        verify(boardService).removeGame(gameEntity);
//    }
//    @Test
//    public void shouldReturnStatusContinueAndChangePlayer() {
//        when(boardService.createMove(eq(moveRequest), eq(gameEntity))).thenReturn(move);
//        when(boardService.isBoardCellAvailable(move)).thenReturn(true);
//        when(boardService.checkGameStatus(gameEntity)).thenReturn(GameStatus.CONTINUE);
//        gameEntity.setCurrentPlayingSymbol('X');
//
//        MovePlayerResponse  response = controller.playerMove(moveRequest);
//        assertEquals(GameStatus.CONTINUE, response.getStatus());
//        assertEquals('X', response.getSymbol());
//        verify(boardService).saveNewMove(move);
//        verify(boardService).changePlayer(any());
//    }
//    @Test
//    public void shouldReturnComputerMove() {
//        Move computerMove = new Move();
//        computerMove.setSymbol('O');
//        when(boardService.makeComputerMove(gameEntity)).thenReturn(computerMove);
//        when(boardService.checkGameStatus(gameEntity)).thenReturn(GameStatus.CONTINUE);
//
//        MoveComputerResponse response = controller.computerMove();
//        assertEquals(GameStatus.CONTINUE, response.getStatus());
//        assertEquals('O', response.getSymbol());
//        verify(boardService).saveNewMove(computerMove);
//        verify(boardService).changePlayer(any());
//    }
//
//}
