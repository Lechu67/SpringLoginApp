package app1.repository;

import app1.model.UserCustom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;


import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOImplTest {

    @Mock
    private JdbcTemplate jdbcTemplateMock;

    @InjectMocks
    private UserDAOImpl userDAO;

    private UserCustom userCustom;
    private UserCustom userCustom2;

    @Before
    public void setup() {
        userCustom = new UserCustom();
        userCustom.setUsername("test");
        userCustom.setPassword("test");
        userCustom.setRole("test");

        userCustom2 = new UserCustom();
        userCustom2.setUsername("test2");
        userCustom2.setPassword("test2");
        userCustom2.setRole(null);
    }
    @Test
    public void shouldAffectOnlyOneRowWithGivenUserParam() {
        String sql = "INSERT INTO users (username,password,role) VALUES (?,?,?)";
        Object[] args = {userCustom.getUsername(), userCustom.getPassword(), userCustom.getAuthorities().toString()};

        when(jdbcTemplateMock.update(sql, args)).thenReturn(1);
        userDAO.insert(userCustom);

        verify(jdbcTemplateMock).update(sql, new Object[]{"test", "test", "[test]"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldAffectOnlyOneRowWiGivenUserParam() {
        String sql = "INSERT INTO users (username,password,role) VALUES (?,?,?)";
        userDAO.insert(userCustom2);
    }
}