package app1.controller;

import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class TicTacToeController {

//    @Autowired
    private BoardService boardService;

//    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/newGame", method = RequestMethod.POST)
    public String newGameView(@RequestParam("user_symbol") char userSymbol) {
        gameService.createNewGame(userSymbol);
        return "tictactoe";
    }


    @RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
    public String tictactoeView() {
        if (gameService.loadGameByUserName(SecurityContextHolder.getContext().getAuthentication().getName()) == null){
            return "newGame";
        }
        return "tictactoe";
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.POST)
    @ResponseBody
    public MoveResponse getMoveRequest(@RequestBody MoveRequest moveRequest) {

        GameEntity currentGameEntity =
                gameService.loadGameByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Move move = createMove(moveRequest,currentGameEntity);
//who is playing ??
        if(!boardService.checkIfBoardCellAvailable(move)){
            return new MoveResponse(GameStatus.TAKEN, "X");
        }
        boardService.saveNewMove(move);
        GameStatus gameStatus = boardService.checkGameStatus(currentGameEntity);
        return new MoveResponse(gameStatus, "X");
    }

    private Move createMove(MoveRequest moveRequest, GameEntity gameEntity) {

        Move move = new Move();
        move.setColumn(moveRequest.getX());
        move.setRow(moveRequest.getY());
        move.setGameID(gameEntity.getId());
        move.setSymbol(gameEntity.getUserSymbol());
        return move;
    }

}
