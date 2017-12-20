package app1.model;





import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "role")
    private String role;
    @ManyToOne(fetch = FetchType.LAZY/*,targetEntity = UserEntity.class*/)
    @JoinColumn(name = "username")
    private UserEntity user;

    public UserRole() {
    }

    public UserRole(String role, UserEntity user) {
        this.role = role;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
