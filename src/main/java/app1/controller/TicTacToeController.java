package app1.controller;

import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TicTacToeController {

    public static final String USER_SYMBOL = "X";
    public static final char EMPTY_FIELD = '\u0000';

    @Autowired
    private BoardService boardService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/newGame", method = RequestMethod.GET)
    public String tictactoeView() {
        if (gameService.loadGameByCurrentUser() == null){
            gameService.createNewGame(USER_SYMBOL);
        }
        return "tictactoe";
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
    @ResponseBody
    public List<BoardResponse> sendPopulatedBoard(UserEntity user) {
        GameEntity currentGameEntity = gameService.loadGameByCurrentUser();
        List<BoardResponse> currentBoard = new ArrayList<>();
        char[][] board = boardService.prepareAndPopulateBoard(currentGameEntity);
        for (int col = 0; col < board[0].length ; col++){
            for (int row = 0; row < board.length ; row++){
                if(board[col][row] != EMPTY_FIELD) {
                    currentBoard.add(new BoardResponse(col, row, board[col][row]));
                }
            }
        }
        return currentBoard;
    }
    @RequestMapping(value = "/playerMove", method = RequestMethod.POST)
    @ResponseBody
    public MovePlayerResponse playerMove(@RequestBody MoveRequest moveRequest) {

        GameEntity currentGameEntity =
                gameService.loadGameByCurrentUser();
            Move move = boardService.createMove(moveRequest,currentGameEntity);
            if(!boardService.isBoardCellAvailable(move)){
                return new MovePlayerResponse(GameStatus.TAKEN, move.getSymbol());
            }
        GameStatus gameStatus = getGameStatusAndUpdateGame(currentGameEntity, move);
        return new MovePlayerResponse(gameStatus,move.getSymbol());
    }
    @RequestMapping(value = "/computerMove", method = RequestMethod.POST)
    @ResponseBody
    public MoveComputerResponse computerMove() {

        GameEntity currentGameEntity =
                gameService.loadGameByCurrentUser();
        Move computerMove = boardService.makeComputerMove(currentGameEntity);
        GameStatus gameStatus = getGameStatusAndUpdateGame(currentGameEntity, computerMove);
        return new MoveComputerResponse(gameStatus,computerMove.getSymbol(),computerMove.getColumn(),computerMove.getRow());
        }

    private GameStatus getGameStatusAndUpdateGame(GameEntity currentGameEntity, Move move) {

        boardService.saveNewMove(move);
        GameStatus gameStatus = boardService.checkGameStatus(currentGameEntity);
        if(gameStatus==GameStatus.WIN || gameStatus == GameStatus.DRAW){
            boardService.removeGame(currentGameEntity);
        }else {
            boardService.changePlayer(currentGameEntity);
        }
        return gameStatus;
    }
}
