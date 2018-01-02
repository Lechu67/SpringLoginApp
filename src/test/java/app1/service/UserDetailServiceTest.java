package app1.service;


import app1.model.UserEntity;
import app1.repository.UserDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.*;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest(User.class)
public class UserDetailServiceTest {

    @InjectMocks
    private UserDetailService service;

    @Mock
    private UserDAO dao;

    private UserEntity userEntity;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockStatic(User.class);
        userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setPassword("test");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldReturnUserNotFoundException(){
        String user = "andrzej";
        when(dao.findByName(userEntity.getUsername())).thenReturn(null);
        service.loadUserByUsername(user);

    }
    @Test
    public void shouldReturnAnInstanceOfUserDetail(){
        String user = "andrzej";
        when(dao.findByName(user)).thenReturn(userEntity);
        assertThat(service.loadUserByUsername(user),instanceOf(UserDetails.class));
    }
    @Test
    public void shouldAddUserWithCorrectParam(){
        String expectedPassword = "test";

        when(encoder.encode(userEntity.getPassword())).thenReturn(expectedPassword);
        service.addUser(userEntity);

        assertEquals("[ROLE_USER]", userEntity.getAuthorities().toString());
        assertEquals(expectedPassword, userEntity.getPassword());
        verify(dao).insert(userEntity);
    }
}