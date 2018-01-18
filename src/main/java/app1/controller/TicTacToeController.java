package app1.controller;

import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TicTacToeController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private GameService gameService;


    /*@RequestMapping(value = "/newGame", method = RequestMethod.POST,headers = {??}, cosumes= ??)
    public String createGame(@RequestParam("user_symbol") String userSymbol) {
        gameService.createNewGame(userSymbol);
        return "tictactoe";
    }*/


    @RequestMapping(value = "/newGame", method = RequestMethod.GET)
    public String tictactoeView() {
        if (gameService.loadGameByCurrentUser() == null){
            gameService.createNewGame("X");
//            return "newGame";
        }
        return "tictactoe";
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
    @ResponseBody
    public List<BoardResponse> sendPopulatedBoard() {

        GameEntity currentGameEntity =
                gameService.loadGameByCurrentUser();

        List<BoardResponse> currentBoard = new ArrayList<>();

        char[][] board = boardService.prepareAndPopulateBoard(currentGameEntity);
        for (int col = 0; col < board[0].length ; col++){
            for (int row = 0; row < board.length ; row++){
                if(board[col][row] != '\u0000') {
                    currentBoard.add(new BoardResponse(col, row, board[col][row]));
                }
            }
        }
        return currentBoard;
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.POST)
    @ResponseBody
    public MoveResponse getMoveRequest(@RequestBody MoveRequest moveRequest) {

        GameEntity currentGameEntity =
                gameService.loadGameByCurrentUser();

        boolean isUserMove = currentGameEntity.isUserNextMove();
        Move move;

        if(isUserMove){
            move = new Move(
                    moveRequest.getX(),
                    moveRequest.getY(),
                    currentGameEntity,
                    currentGameEntity.getUserSymbol());
            if(!boardService.isBoardCellAvailable(move)){
                return new MoveResponse(GameStatus.TAKEN, move.getSymbol());
            }
        }
        else {
//            move = boardService.makeComputerMove(currentGameEntity);
            move = new Move(
                    moveRequest.getX(),
                    moveRequest.getY(),
                    currentGameEntity,
                    'O');
            if(!boardService.isBoardCellAvailable(move)){
                return new MoveResponse(GameStatus.TAKEN, move.getSymbol());
            }
        }

        boardService.saveNewMove(move);
        GameStatus gameStatus = boardService.checkGameStatus(currentGameEntity);
        if(gameStatus==GameStatus.WIN || gameStatus == GameStatus.DRAW){
            boardService.removeGame(currentGameEntity);
        }else {
            boardService.changePlayer(currentGameEntity);
        }

        return new MoveResponse(gameStatus,move.getSymbol());
    }
}
