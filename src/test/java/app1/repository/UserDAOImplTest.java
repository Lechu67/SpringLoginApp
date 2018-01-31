package app1.repository;

import app1.config.RepoConfig;
import app1.model.UserEntity;
import app1.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { RepoConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class UserDAOImplTest {

    //http://www.baeldung.com/spring-jpa-test-in-memory-database

    @Autowired
    private UserDAO dao;

    private UserEntity userEntity;

    @Before
    public void prepareUser(){
        userEntity = new UserEntity();
        userEntity.setUsername("john");
        Set<UserRole> roles =
                Stream.of(new UserRole("ROLE_USER",userEntity)).collect(Collectors.toCollection(HashSet::new));
        userEntity.setRoles(roles);
    }
    @Test
    public void shouldFindAndAddAUserToH2Db (){
        dao.insert(userEntity);
        UserEntity user2 = dao.findByName("john");

        assertEquals("john",user2.getUsername());
        assertEquals("[ROLE_USER]",user2.getAuthorities().toString());
    }
}