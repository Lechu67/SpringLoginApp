package app1.model;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "username")
    private Set<Role>  roles = new HashSet<Role>(0);

    public UserEntity() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
       /* for(Role role : getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }*/
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole())));
        return !authorities.isEmpty() ? authorities : null;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
