package app1.repository;

import app1.config.RepoConfig;
import app1.model.GameEntity;
import app1.model.UserEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes ={RepoConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class GameDAOImplTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private GameDAO gameDAO;

    @Before
    public void setup(){
        executeSqlScript("insert-data.sql",false);
    }
    @After
    public void end(){
        executeSqlScript("delete.sql",false);
    }
    @Test
    public void test1(){
        UserEntity user = new UserEntity();
        user.setUsername("a");
        GameEntity gameEntity = gameDAO.findGameByUserName(user);
        assertThat(gameEntity.getUser().getUsername(), is(user.getUsername()));
    }
}