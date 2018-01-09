package app1.winStrategy;

public class HorizontalWinStrategy implements WinStrategy{
    @Override
    public Character isWin(char[][] board) {

        for (int row = 0 ; row < board.length ; row++){
            char symbol = board[row][0];
            for (int col = 1 ; col < board[row].length ; col++){
                char nextSymbol = board[row][col];
                if(nextSymbol != symbol){
                    break;
                }
                if(col == board[row].length -1){
                    return symbol;
                }
            }
        }
        return null;
    }
}
