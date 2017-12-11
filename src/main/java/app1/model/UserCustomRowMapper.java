package app1.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCustomRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserCustom userCustom = new UserCustom();
        userCustom.setId(rs.getInt("ID"));
        userCustom.setUsername(rs.getString("USERNAME"));
        userCustom.setPassword(rs.getString("PASSWORD"));
        userCustom.setRole(rs.getString("ROLE"));
        return userCustom;
    }
}
