package app1.service;

import app1.computerMoveStrategy.Difficulty;
import app1.model.GameEntity;
import app1.model.Move;
import app1.model.MoveRequest;
import app1.model.UserEntity;
import app1.repository.GameDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;
    @Mock
    private GameDAO gameDAO;

    public GameEntity gameEntity;

    @Before
    public void setup() {
        gameEntity = new GameEntity();
    }

    @Test
    public void shouldCallSaveDAOMethod() {
        gameService.createNewGame(gameEntity.getUserSymbol(),gameEntity.getUser(),Difficulty.EASY);
        verify(gameDAO).saveNewGame(any(GameEntity.class));
    }
}
