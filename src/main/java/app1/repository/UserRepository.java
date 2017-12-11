package app1.repository;


import app1.model.UserCustom;

public interface UserRepository {

    UserCustom findByUserName(String name);
}
