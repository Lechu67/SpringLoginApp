package app1.model;

public class MoveRequest {

    int x;
    int y;

    public MoveRequest() {
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

    @Override
    public String toString() {
        return "MoveRequest{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
