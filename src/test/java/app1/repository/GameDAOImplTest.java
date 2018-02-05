package app1.repository;

import app1.config.RepoConfig;
import app1.model.GameEntity;
import app1.model.Move;
import app1.model.UserEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.instanceOf;
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

    private GameEntity gameEntity;
    private UserEntity user;

    @Before
    public void setup(){
        executeSqlScript("insert-data.sql",false);
        gameEntity = new GameEntity('X',true,user,3);
        gameEntity.setId(1);
        user = new UserEntity("a", "a");
    }
    @After
    public void end(){
//        executeSqlScript("delete.sql",false);
        dropTables("game","move","users");
    }
    @Test
    public void shouldFindGameEntityForGivenUser(){
        GameEntity gameEntity = gameDAO.findGameByUserName(user);
        assertThat(gameEntity.getUser().getUsername(), is(user.getUsername()));
    }
    @Test
    public void shouldReturnNullBecauseOfGameNotFind(){
        user.setUsername("b");
        GameEntity gameEntity = gameDAO.findGameByUserName(user);
        assertEquals(null, gameEntity);
    }
    @Test
    public void shouldReturnMovePossibleTrue(){
        Move move = new Move(0,0,gameEntity,'X');
        assertEquals(true,gameDAO.isMovePossible(move));
    }
    @Test
    public void shouldReturnMovePossibleFalse(){
        Move move = new Move(1,1,gameEntity,'X');
        assertEquals(false,gameDAO.isMovePossible(move));
    }
    @Test
    public void shouldFindOneMove(){
        assertThat(gameDAO.findMovesByGame(gameEntity).size(),is(1));
        assertThat(gameDAO.findMovesByGame(gameEntity).get(0), instanceOf(Move.class));
    }
//    do sprawdzewnia
    /*@Test
    public void shouldSaveNewMove(){
        Move move = new Move(2,0,gameEntity,'X');
        assertThat(countRowsInTable("move"),is(1));
        gameDAO.saveNewMove(move);
        int a= countRowsInTable("move");
        assertThat(countRowsInTable("move"),is(2));
    }*/
    @Test
    public void shouldRemoveGameAndMoves(){
        gameDAO.removeGameAndMoves(gameEntity);
        assertThat(countRowsInTable("move"),is(0));
        GameEntity gameEntity = gameDAO.findGameByUserName(user);
        assertEquals(null, gameEntity);
    }

}