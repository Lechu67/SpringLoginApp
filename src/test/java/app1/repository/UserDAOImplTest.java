package app1.repository;

import app1.config.RepoConfig;
import app1.model.UserEntity;
import app1.model.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
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


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { RepoConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class UserDAOImplTest {

    //http://www.baeldung.com/spring-jpa-test-in-memory-database

    @Resource
    private UserDAO dao;

    @Test
    public void shouldFindAndAddAUserToH2Db (){
        UserEntity user = new UserEntity();
        user.setUsername("john");
        Set<UserRole> roles = Stream.of(new UserRole("ROLE_USER",user)).collect(Collectors.toCollection(HashSet::new));
        user.setRoles(roles);

        dao.insert(user);
        UserEntity user2 = dao.findByName("john");

        assertEquals("john",user2.getUsername());
        assertEquals("[ROLE_USER]",user2.getAuthorities().toString());
    }
}