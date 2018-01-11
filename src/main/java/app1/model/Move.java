package app1.model;

import javax.persistence.*;

@Entity
@Table(name = "move")
public class Move {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "col")
    private int column;

    @Column(name = "row")
    private int row;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = GameEntity.class)
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @Column(name = "symbol")
    private char symbol;

    public Move() {
    }

    public Move(int column,int row, GameEntity gameEntity, char symbol) {
        this.row = row;
        this.column = column;
        this.game = gameEntity;
        this.symbol = symbol;
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

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
