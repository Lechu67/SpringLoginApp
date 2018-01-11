package app1.controller;

import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
        if (gameService.loadGameByUserName(SecurityContextHolder.getContext().getAuthentication().getName()) == null){
            gameService.createNewGame("X");
//            return "newGame";
        }
        return "tictactoe";
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.POST)
    @ResponseBody
    public MoveResponse getMoveRequest(@RequestBody MoveRequest moveRequest) {

        GameEntity currentGameEntity =
                gameService.loadGameByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        boolean isUserMove = currentGameEntity.isUserNextMove();
        Move move;

        if(isUserMove){
            move = new Move(
                    moveRequest.getX(),
                    moveRequest.getY(),
                    currentGameEntity.getId(),
                    currentGameEntity.getUserSymbol());
            if(!boardService.isBoardCellAvailable(move)){
                return new MoveResponse(GameStatus.TAKEN, move.getSymbol());
            }
        }
        else {
            move = boardService.makeComputerMove(currentGameEntity);
        }

        boardService.saveNewMove(move);
        GameStatus gameStatus = boardService.checkGameStatus(currentGameEntity);
        boardService.changePlayer(currentGameEntity);
        return new MoveResponse(gameStatus,move.getSymbol());
    }
}
