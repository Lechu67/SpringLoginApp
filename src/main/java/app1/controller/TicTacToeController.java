package app1.controller;

import app1.computerMoveStrategy.Difficulty;
import app1.config.ActiveUser;
import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class TicTacToeController {

    private static final char USER_SYMBOL = 'X';
    private static final char COMPUTER_SYMBOL = 'O';

    @Autowired
    private BoardService boardService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/newGame", method = RequestMethod.GET)
    public String tictactoeView(@ActiveUser UserEntity userEntity) {
        if (gameService.loadGameByUser(userEntity) == null) {
            gameService.createNewGame(USER_SYMBOL,COMPUTER_SYMBOL, userEntity, Difficulty.EASY);
        }
        return "tictactoe";
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
    @ResponseBody
    public BoardSymbolsResponse sendPopulatedBoard(@ActiveUser UserEntity userEntity) {
        GameEntity currentGameEntity = gameService.loadGameByUser(userEntity);
        return new BoardSymbolsResponse(getBoardResponses(currentGameEntity), currentGameEntity.getCurrentPlayingSymbol());
    }

    @RequestMapping(value = "/playerMove", method = RequestMethod.POST)
    @ResponseBody
    public MovePlayerResponse playerMove(@RequestBody MoveRequest moveRequest, @ActiveUser UserEntity userEntity) {

        GameEntity currentGameEntity =
                gameService.loadGameByUser(userEntity);
            Move move = boardService.createMove(moveRequest,currentGameEntity);

            if (!isPlayerTurn(currentGameEntity)) {
                return new MovePlayerResponse(GameStatus.NOT_YOUR_TURN,move.getSymbol());
            }
            if(!boardService.isBoardCellAvailable(move)){
                return new MovePlayerResponse(GameStatus.TAKEN, move.getSymbol());
            }
        GameStatus gameStatus = saveMoveAndGetGameStatus(currentGameEntity, move);
        return new MovePlayerResponse(gameStatus,move.getSymbol());
    }

    private boolean isPlayerTurn(GameEntity currentGameEntity) {
        return currentGameEntity.getCurrentPlayingSymbol() == currentGameEntity.getUserSymbol();
    }

    @RequestMapping(value = "/computerMove", method = RequestMethod.POST)
    @ResponseBody
    public MoveComputerResponse computerMove(@ActiveUser UserEntity userEntity) {
        GameEntity currentGameEntity =
                gameService.loadGameByUser(userEntity);
        if (!isComputerTurn(currentGameEntity)) {
            return new MoveComputerResponse(GameStatus.NOT_YOUR_TURN,currentGameEntity.getComputerSymbol());
        }
        Move computerMove = boardService.makeComputerMove(currentGameEntity);
        GameStatus gameStatus = saveMoveAndGetGameStatus(currentGameEntity, computerMove);
        return new MoveComputerResponse(gameStatus,computerMove.getSymbol(),computerMove.getColumn(),computerMove.getRow());
    }

    private boolean isComputerTurn(GameEntity currentGameEntity) {
        return currentGameEntity.getCurrentPlayingSymbol() == currentGameEntity.getComputerSymbol();
    }

    private List<BoardResponse> getBoardResponses(GameEntity currentGameEntity) {
        Board board = boardService.getActualBoard(currentGameEntity);
        return board.getMoves()
                .stream()
                .map(move ->
                        new BoardResponse(move.getColumn(),
                                move.getRow(),
                                move.getSymbol()))
                .collect(Collectors.toList());
    }
    private GameStatus saveMoveAndGetGameStatus(GameEntity currentGameEntity, Move move) {
        boardService.saveNewMove(move);
        return removeGameIfEndOrChangePlayer(currentGameEntity);
    }

    private GameStatus removeGameIfEndOrChangePlayer(GameEntity currentGameEntity) {
        GameStatus gameStatus = boardService.checkGameStatus(currentGameEntity);
        if(GameStatus.isEndGame(gameStatus)){
            boardService.removeGame(currentGameEntity);
        }else {
            boardService.changePlayer(currentGameEntity);
        }
        return gameStatus;
    }

//TODO
//    public GameStatus move(Symbol symbol, int position) {
        // kto sie kryje za symbolem
        // jezeli komputer to ustalamy position = algorytm
        // wykonujemy ruch moveInternal(User)
        // sprawdzamy status gry
        // jezeli koniec gry to wyjscie
        // jezeli nastepny gracz to komputer to powtorz od kroku 2
        // zwroc status
//    }
}
