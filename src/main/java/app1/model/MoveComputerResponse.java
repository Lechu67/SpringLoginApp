package app1.model;

public class MoveComputerResponse extends MovePlayerResponse {

    int x;
    int y;

    public MoveComputerResponse(GameStatus status, char symbol, int x, int y) {
        super(status, symbol);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
