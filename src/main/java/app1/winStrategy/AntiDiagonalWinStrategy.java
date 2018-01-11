package app1.winStrategy;

public class AntiDiagonalWinStrategy implements WinStrategy {
    @Override
    public Character isWin(char[][] board) {

        for (int col = 0; col < board.length ; col++){
            char symbol = board[board.length-1][col];
            for (int row = board.length-1; row >= 0 ; row--){
                 char nextSymbol = board[row][col];
                 if(nextSymbol != symbol){
                     break;
                 }
                 if(row == 0){
                     return nextSymbol;
                 }
            }
        }
            return null;
    }
}
