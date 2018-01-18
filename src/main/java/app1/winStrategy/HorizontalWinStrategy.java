package app1.winStrategy;

public class HorizontalWinStrategy implements WinStrategy {
    @Override
    public Character isWin(char[][] board) {

        for (int col = 0; col < board[0].length ; col++){
            char symbol = board[0][col];
            for (int row = 1; row < board.length ; row++){
                char nextSymbol = board[row][col];
                if(nextSymbol != symbol){
                    break;
                }
                if(row == board.length -1 && symbol!='\u0000'){
                    return symbol;
                }
            }
        }
        return null;
    }
}
