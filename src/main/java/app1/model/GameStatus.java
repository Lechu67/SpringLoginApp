package app1.model;

public enum GameStatus {

    TAKEN,
    CONTINUE,
    WIN,
    DRAW,
    NOT_YOUR_TURN;

    public static boolean isEndGame(GameStatus gameStatus) {
        return gameStatus==GameStatus.WIN || gameStatus == GameStatus.DRAW;
    }
}
