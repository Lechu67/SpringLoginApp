package app1.repository;

import app1.model.UserCustom;
import app1.model.UserCustomRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(UserDetails user) {
        String sql = "INSERT INTO users " + "(username,password,role) VALUES (?,?,?)";
        jdbcTemplate.update(sql, new Object[]{user.getUsername(), user.getPassword(), "USER"});
    }

        /*       Before: */
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
    @Override
    public UserCustom findByName(String username) {
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive() + "HEEEEEEEEEEEEREEEEEEEE");
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            UserCustom userCustom =
                    (UserCustom) jdbcTemplate.queryForObject(sql, new Object[]{username}, new UserCustomRowMapper());
            return userCustom;
        } catch(IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    /*Before*/
//        Connection connection = null;
//        try {
//            connection = repoConfig.dataSource().getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, "username");
//            UserCustom user = null;
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                user = new UserCustom(
//                        resultSet.getInt("ID"),
//                        resultSet.getString("username"),
//                        resultSet.getString("password"),
//                        resultSet.getString("role")
//                );
//            }
//            resultSet.close();
//            preparedStatement.close();
//            return user;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException("Couldn't close the connection");
//                }
//            }
//        }
//    }
    @Override
    public UserCustom findById(int id) {
        String sql = "SELECT * FROM users WHERE ID = ?";
        UserCustom userCustom = (UserCustom) jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserCustomRowMapper());
        return userCustom;
        /*Before*/
//            Connection connection = null;
//            try {
//                connection = repoConfig.dataSource().getConnection();
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setInt(1,id);
//                UserCustom user = null;
//                ResultSet resultSet = preparedStatement.executeQuery();
//                if(resultSet.next()){
//                    user = new UserCustom(
//                            resultSet.getInt("ID"),
//                            resultSet.getString("username"),
//                            resultSet.getString("password"),
//                            resultSet.getString("role")
//                    );
//                }
//                resultSet.close();
//                preparedStatement.close();
//                return user;
//            }catch(SQLException e) {
//                throw new RuntimeException(e);
//            }finally {
//                if(connection!=null){
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        throw new RuntimeException("Couldn't close the connection");
//                    }
//                }
//            }
//        }
    }
}

