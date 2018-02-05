package app1.winStrategy;

public class AntiDiagonalWinStrategy implements WinStrategy {

    public static final char EMPTY_FIELD = '\u0000';

    @Override
    public Character isWin(char[][] board) {

            int row = 0;
            char symbol = board[board.length-1][row];
            for (int col = board.length-1; col >= 0 ; col--){
                 char nextSymbol = board[col][row++];
                 if(nextSymbol != symbol){
                     break;
                 }
                 if(col == 0 && symbol!= EMPTY_FIELD){
                     return symbol;
                 }
            }
            return null;
    }
}
