package app1.repository;

import app1.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDAO {

    void insert(UserDetails user);
    UserEntity findByName(String username);
}
