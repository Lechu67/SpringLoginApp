package app1.repository;

import app1.config.RepoConfig;
import app1.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
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
    public void testAdd (){
        UserEntity user = new UserEntity();
        user.setUsername("john");
        dao.insert(user);

        UserEntity user2 = dao.findByName("john");
        assertEquals("john",user2.getUsername());
    }
}