package app1.repository;


import app1.model.UserCustom;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.asList;

//@Repository
public class UserRepositoryHardCoded implements UserRepository, InitializingBean{

    @Autowired
    private PasswordEncoder encoder;

    private List<UserCustom> listOfUserCustoms;

    @Override
    public UserCustom findByUserName(String name) {
        return listOfUserCustoms
                .stream()
                .filter(userCustom -> userCustom.getUsername().equals(name))
                .findFirst()
                .get();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        listOfUserCustoms = asList(
                new UserCustom(1,"zenek",encoder.encode("zenek123"),"ADMIN" ),
                new UserCustom(2,"test",encoder.encode("test"), "ADMIN" ),
                new UserCustom(3,"Leszek",encoder.encode("Leszek123"),"USER" ));
    }
}

