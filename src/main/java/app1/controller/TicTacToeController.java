package app1.controller;

import app1.model.GameStatus;
import app1.model.MoveRequest;
import app1.model.MoveResponse;
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
    public String newGameView(@RequestParam("symbol") char symbol) {
        gameService.makeNewGame(symbol);
        return "tictactoe";
    }


    @RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
    public String tictactoeView() {
        if (gameService.findGameByUser(SecurityContextHolder.getContext().getAuthentication().getName()) == null){
            return "newGame";
        }
        return "tictactoe";
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.POST)
    @ResponseBody
    public MoveResponse getMoveRequest(@RequestBody MoveRequest moveRequest) {

        if(!boardService.checkIfBoardCellAvailable(moveRequest)){
//            return...
        }
        return new MoveResponse(GameStatus.ISFREE, "X");
//        return "tictactoe";
    }

}
