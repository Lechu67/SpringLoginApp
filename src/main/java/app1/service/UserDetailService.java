package app1.service;


import app1.model.UserCustom;
import app1.model.UserEntity;
import app1.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity user = userDAO.findByName(userName);
        if (user== null) {
            throw new UsernameNotFoundException(userName);
        }
        return toUserDetails(user);
    }
    public void addUser(UserCustom user){
        user.setRole("USER");
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.insert(toUserDetails(user));
    }
    private UserDetails toUserDetails(UserEntity userEntity){
        return User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(role
                .build();
    }
}
