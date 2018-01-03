package app1.model;

import javax.persistence.*;

@Entity
@Table(name = "moves")
public class Moves {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "row")
    private int row;

    @Column(name = "column")
    private int column;

    @Column(name = "game_id")
    private int gameID;

    @Column(name = "symbol")
    private char userSymbol;

    public Moves() {
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
