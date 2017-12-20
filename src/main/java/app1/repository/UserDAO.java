package app1.repository;

import app1.model.UserEntity;

public interface UserDAO {

    void insert(UserEntity userEntity);
    UserEntity findByName(String username);
   // List<String> getUserRoles(String username);
}
