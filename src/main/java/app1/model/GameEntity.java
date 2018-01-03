package app1.model;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class GameEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "dimension")
    private int dimension;

    @Column(name = "symbol")
    private char userSymbol;

    @Column(name = "is_user_next_move")
    private boolean isUserNextMove;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private UserEntity user;

    public GameEntity() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
