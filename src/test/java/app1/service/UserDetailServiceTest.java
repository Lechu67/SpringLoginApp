package app1.service;


import app1.model.UserCustom;
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

    @Mock
    private UserBuilder userBuilder;


    private UserCustom userCustom;
    private UserDetails userDetails;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockStatic(User.class);
        userDetails = new UserCustom();
        userCustom = new UserCustom();
        userCustom.setUsername("test");
        userCustom.setPassword("test");
        userCustom.setRole("USER");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldReturnUserNotFoundException(){
        String user = "andrzej";
        when(dao.findByName(userCustom.getUsername())).thenReturn(null);
        service.loadUserByUsername(user);

    }
    @Test
    public void shouldReturnAnInstanceOfUserDetail(){
        String user = "andrzej";
        userBuilderMock();
        when(dao.findByName(user)).thenReturn(userCustom);
        assertThat(service.loadUserByUsername(user),instanceOf(UserDetails.class));
    }
    @Test
    public void shouldInertAddUserWithCorrectParam(){
        String expectedPassword = "test";

        userBuilderMock();

        when(encoder.encode(userCustom.getPassword())).thenReturn(expectedPassword);
        service.addUser(userCustom);

        assertEquals("USER",userCustom.getRole());
        assertEquals(expectedPassword,userCustom.getPassword());
        verify(dao).insert(userDetails);
    }

    private void userBuilderMock() {
        when(User.withUsername(userCustom.getUsername())).thenReturn(userBuilder);
        when(userBuilder.password(userCustom.getPassword())).thenReturn(userBuilder);
        when(userBuilder.roles(userCustom.getRole())).thenReturn(userBuilder);
        when(userBuilder.build()).thenReturn(userDetails);
    }


}