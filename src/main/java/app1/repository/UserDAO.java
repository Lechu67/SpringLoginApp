package app1.repository;

import app1.model.UserCustom;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDAO {

    void insert(UserDetails user);
    UserCustom findByName(String username);
}
