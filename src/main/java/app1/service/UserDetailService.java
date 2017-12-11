package app1.service;


import app1.config.WebSecurityConfig;
import app1.model.UserCustom;
import app1.repository.UserDAO;
import app1.repository.UserDAOImpl;
import app1.repository.UserRepositoryHardCoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserDAOImpl userDAO;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserCustom user = userDAO.findByName(userName);
        if (user== null) {
            throw new UsernameNotFoundException(userName);
        }

        return toUserDetails(user);
    }

    public void addUser(UserCustom user){
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.insert(toUserDetails(user));
    }


    private UserDetails toUserDetails(UserCustom userCustom){
        return User.withUsername(userCustom.getUsername())
                .password(userCustom.getPassword())
                .roles(userCustom.getRole())
                .build();
    }
}
