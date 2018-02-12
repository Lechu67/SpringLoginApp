package app1.controller;

import app1.computerMoveStrategy.Difficulty;
import app1.model.*;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String tictactoeView() {
        if (gameService.loadGameByCurrentUser(getCurrentUser()) == null) {
            gameService.createNewGame(USER_SYMBOL,COMPUTER_SYMBOL, getCurrentUser(), Difficulty.EASY);
        }
        return "tictactoe";
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
    @ResponseBody
    public BoardSymbolsResponse sendPopulatedBoard() {
        GameEntity currentGameEntity = gameService.loadGameByCurrentUser(getCurrentUser());
        return new BoardSymbolsResponse(getBoardResponses(currentGameEntity), currentGameEntity.getCurrentPlayingSymbol());
    }

    @RequestMapping(value = "/playerMove", method = RequestMethod.POST)
    @ResponseBody
    public MovePlayerResponse playerMove(@RequestBody MoveRequest moveRequest) {

        GameEntity currentGameEntity =
                gameService.loadGameByCurrentUser(getCurrentUser());
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
    public MoveComputerResponse computerMove() {

        GameEntity currentGameEntity =
                gameService.loadGameByCurrentUser(getCurrentUser());
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
