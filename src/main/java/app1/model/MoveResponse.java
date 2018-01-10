package app1.model;

public class MoveResponse {

    private GameStatus status;
    private char symbol;

    public MoveResponse(GameStatus status, char symbol) {
        this.status = status;
        this.symbol = symbol;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
