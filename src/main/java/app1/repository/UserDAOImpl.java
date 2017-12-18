package app1.repository;

import app1.model.UserCustom;
import app1.model.UserCustomRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(UserDetails user) {
        String sql = "INSERT INTO users (username,password,role) VALUES (?,?,?)";
        jdbcTemplate.update(
                sql,
                new Object[]{user.getUsername(),
                user.getPassword(),
                user.getAuthorities().toString()});
    }
    @Override
    public UserCustom findByName(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            UserCustom userCustom =
                    (UserCustom) jdbcTemplate.queryForObject(sql, new Object[]{username}, new UserCustomRowMapper());
            return userCustom;
        } catch(IncorrectResultSizeDataAccessException e){
            return null;
        }
    }
}

