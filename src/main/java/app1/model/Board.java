package app1.model;

import app1.winStrategy.*;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public GameEntity gameEntity;
    private List<Move> moves;
    private char[][] board;

    public Board(GameEntity gameEntity, List<Move> moves) {
        this.gameEntity = gameEntity;
        this.moves= moves;
        prepareAndPopulateBoard(moves);
    }

    public char[][] getBoard() {
        return board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public void setGameEntity(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }


    public char[][] prepareAndPopulateBoard(List<Move> moves){
        board = new char[gameEntity.getDimension()][gameEntity.getDimension()];
        moves.forEach(m -> {
            int x = m.getColumn();
            int y = m.getRow();
            board[x][y] = m.getSymbol();
        });
        return board;
    }
    public GameStatus checkGameStatus(){
        if(tryGetWinner() != null){
            return GameStatus.WIN;
        } else if(checkIfDraw()){
            return GameStatus.DRAW;
        }else{
            return GameStatus.CONTINUE;
        }
    }
    private boolean checkIfDraw() {
        for (int row = 0 ; row < board.length ; row++){
            for (int col = 0 ; col < board[row].length ; col++){
                if(board[row][col] == 0){
                    return false;
                }
            }
        }
        return true;
    }
    private Character tryGetWinner() {
        List<WinStrategy> winStrategies = new ArrayList<>();
        winStrategies.add(new VerticalWinStrategy());
        winStrategies.add(new HorizontalWinStrategy());
        winStrategies.add(new DiagonalWinStrategy());
        winStrategies.add(new AntiDiagonalWinStrategy());

        Character winner = null;
        for (WinStrategy winStrategy : winStrategies){
            winner = winStrategy.isWin(board);
            if(winner != null){
                return winner;
            }
        }
        return null;
    }
}
