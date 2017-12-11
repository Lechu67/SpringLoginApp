package app1.repository;

import app1.model.UserCustom;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDAO {

    void insert(UserDetails user);
    UserCustom findById(int id);
    UserCustom findByName(String username);
}
