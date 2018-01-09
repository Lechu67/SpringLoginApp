package app1.winStrategy;

public class DiagonalWinStrategy implements WinStrategy {
    @Override
    public Character isWin(char[][] board) {

        for (int i = 1 ; i < board.length ; i++){
            char nextSymbol = board[i][i];
            if(nextSymbol != board[0][0]){
                break;
            }
            if(i == board.length -1){
                return nextSymbol;
            }
        }
        return null;
    }
}
