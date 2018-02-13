package app1.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    @Column(name = "user_symbol")
    private char userSymbol;

    @Column(name = "computer_symbol")
    private char computerSymbol;

    @Column(name = "playing_symbol")
    private char currentPlayingSymbol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private UserEntity user;

    @Column(name = "dimension")
    private int dimension;

    @Column(name = "difficulty")
    private String difficulty;

    public GameEntity() {
    }

    public GameEntity(char userSymbol,char computerSymbol, char currentPlayingSymbol, UserEntity user, int dimension, String difficulty) {
        this.userSymbol = userSymbol;
        this.computerSymbol = computerSymbol;
        this.currentPlayingSymbol = currentPlayingSymbol;
        this.user = user;
        this.dimension=dimension;
        this.difficulty=difficulty;
    }

    public char getCurrentPlayingSymbol() {
        return currentPlayingSymbol;
    }

    public void setCurrentPlayingSymbol(char currentPlayingSymbol) {
        this.currentPlayingSymbol = currentPlayingSymbol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public char getUserSymbol() {
        return userSymbol;
    }

    public void setUserSymbol(char userSymbol) {
        this.userSymbol = userSymbol;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public char getComputerSymbol() {
        return computerSymbol;
    }

    public void setComputerSymbol(char computerSymbol) {
        this.computerSymbol = computerSymbol;
    }
}
