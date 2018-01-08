package app1.model;

import javax.persistence.*;

@Entity
@Table(name = "move")
public class Move {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "row")
    private int row;

    @Column(name = "column")
    private int column;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "game_id")
    private int gameID;

    @Column(name = "symbol")
    private char userSymbol;

    public Move() {
    }

    public Move(int row, int column, int gameID, char userSymbol) {
        this.row = row;
        this.column = column;
        this.gameID = gameID;
        this.userSymbol = userSymbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public char getUserSymbol() {
        return userSymbol;
    }

    public void setUserSymbol(char userSymbol) {
        this.userSymbol = userSymbol;
    }
}
