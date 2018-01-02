package app1.service;


import app1.model.UserRole;
import app1.model.UserEntity;
import app1.repository.UserDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
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
        Set<UserRole> roles = new HashSet<>();
        for (UserRole role : user.getRoles()) {
            roles.add(new UserRole(role.getRole(),user));
        }
        user.setRoles(roles);
        UserDetails userDetails = new User(user.getUsername(),user.getPassword(),user.getAuthorities());
        return userDetails;
    }
    public void addUser(UserEntity user){

        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole("ROLE_USER",user));
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.insert(user);
    }
}
