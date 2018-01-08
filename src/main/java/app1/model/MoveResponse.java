package app1.model;

public class MoveResponse {

    //1 - win, 2 - continue game
    private GameStatus status;

    private String symbol;

    public MoveResponse(GameStatus status, String symbol) {
        this.status = status;
        this.symbol = symbol;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
