package app1.repository;

import app1.config.RepoConfig;
import app1.model.UserCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RepoConfig repoConfig;

//    @Autowired
//    private DataSource dataSource;
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//    @Override
//    public void insert(UserDetails user) {
//
//        String sql = "INSERT INTO users " + "(username,password,role) VALUES (?,?,?)";
//        jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcTemplate.update(sql,new Object[]{user.getUsername(),user.getPassword(),"USER"});

//        try{
//            connection = repoConfig.dataSource().getConnection();
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1,user.getUsername());
//            statement.setString(2, user.getPassword());
//            statement.setString(3,"USER");
//            statement.executeUpdate();
//            statement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if(connection!=null){
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException("Couldn't close the connection");
//                }
//            }
//        }
//    }

    @Override
    public void insert(UserDetails user) {

        String sql = "INSERT INTO users " + "(username,password,role) VALUES (?,?,?)";
        Connection connection= null;

        try{
            connection = repoConfig.dataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3,"USER");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Couldn't close the connection");
                }
            }
        }
    }

    @Override
    public UserCustom findByName(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";
        Connection connection = null;
        try {
            connection = repoConfig.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "username");
            UserCustom user = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new UserCustom(
                        resultSet.getInt("ID"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );
            }
            resultSet.close();
            preparedStatement.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Couldn't close the connection");
                }
            }
        }
    }
    @Override
        public UserCustom findById(int id) {

            String sql = "SELECT * FROM users WHERE ID = ?";
            Connection connection = null;
            try {
                connection = repoConfig.dataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,id);
                UserCustom user = null;
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    user = new UserCustom(
                            resultSet.getInt("ID"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("role")
                    );
                }
                resultSet.close();
                preparedStatement.close();
                return user;
            }catch(SQLException e) {
                throw new RuntimeException(e);
            }finally {
                if(connection!=null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException("Couldn't close the connection");
                    }
                }
            }
    }
}
