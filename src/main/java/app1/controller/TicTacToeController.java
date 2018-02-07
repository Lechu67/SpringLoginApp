package app1.controller;

import app1.computerMoveStrategy.Difficulty;
import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TicTacToeController {

    private static final char USER_SYMBOL = 'X';
    private static final char EMPTY_FIELD = '\u0000';

    @Autowired
    private BoardService boardService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/newGame", method = RequestMethod.GET)
    public String tictactoeView() {
        if (gameService.loadGameByCurrentUser(getCurrentUser()) == null){
            gameService.createNewGame(USER_SYMBOL,getCurrentUser(),Difficulty.EASY);
        }
        return "tictactoe";
    }
    //wybrac kto gra, zapisac ostatni symbol, jak jest ruch gracza komunikat.

    @RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
    @ResponseBody
    public List<BoardResponse> sendPopulatedBoard() {
        GameEntity currentGameEntity = gameService.loadGameByCurrentUser(getCurrentUser());
        return getBoardResponses(currentGameEntity);
    }

    @RequestMapping(value = "/playerMove", method = RequestMethod.POST)
    @ResponseBody
    public MovePlayerResponse playerMove(@RequestBody MoveRequest moveRequest) {

        GameEntity currentGameEntity =
                gameService.loadGameByCurrentUser(getCurrentUser());
            Move move = boardService.createMove(moveRequest,currentGameEntity);
            if(!boardService.isBoardCellAvailable(move)){
                return new MovePlayerResponse(GameStatus.TAKEN, move.getSymbol());
            }
        GameStatus gameStatus = saveMoveAndGetGameStatus(currentGameEntity, move);
        return new MovePlayerResponse(gameStatus,move.getSymbol());
    }
    @RequestMapping(value = "/computerMove", method = RequestMethod.POST)
    @ResponseBody
    public MoveComputerResponse computerMove() {

        GameEntity currentGameEntity =
                gameService.loadGameByCurrentUser(getCurrentUser());
        Move computerMove = boardService.makeComputerMove(currentGameEntity);//
        GameStatus gameStatus = saveMoveAndGetGameStatus(currentGameEntity, computerMove);
        return new MoveComputerResponse(gameStatus,computerMove.getSymbol(),computerMove.getColumn(),computerMove.getRow());
        }

    private List<BoardResponse> getBoardResponses(GameEntity currentGameEntity) {
        List<BoardResponse> currentBoard = new ArrayList<>();
        char[][] board = boardService.getActualBoard(currentGameEntity).getBoard();
        for (int col = 0; col < board[0].length ; col++){
            for (int row = 0; row < board.length ; row++){
                if(board[col][row] != EMPTY_FIELD) {
                    currentBoard.add(new BoardResponse(col, row, board[col][row]));
                }
            }
        }
        return currentBoard;
    }
    private GameStatus saveMoveAndGetGameStatus(GameEntity currentGameEntity, Move move) {
        boardService.saveNewMove(move);
        return getGameStatus(currentGameEntity);
    }

    private GameStatus getGameStatus(GameEntity currentGameEntity) {
        GameStatus gameStatus = boardService.checkGameStatus(currentGameEntity);
        if(isEndGame(gameStatus)){
            boardService.removeGame(currentGameEntity);
        }else {
            boardService.changePlayer(currentGameEntity);
        }
        return gameStatus;
    }

    private boolean isEndGame(GameStatus gameStatus) {
        return gameStatus==GameStatus.WIN || gameStatus == GameStatus.DRAW;
    }
    private UserEntity getCurrentUser() {
        UserDetails currentUser =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserEntity(
                currentUser.getUsername(),
                currentUser.getPassword()
        );
    }
}
