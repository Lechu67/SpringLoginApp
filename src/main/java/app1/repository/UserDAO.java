package app1.repository;

import app1.model.UserEntity;
import app1.model.UserRole;

public interface UserDAO {

    void insert(UserEntity userEntity);

    UserEntity findByName(String username);

}
