package app1.service;

        import app1.computerMoveStrategy.ComputerMoveStrategy;
        import app1.computerMoveStrategy.Difficulty;
        import app1.computerMoveStrategy.EasyStrategy;
        import app1.model.GameEntity;
        import app1.model.Move;
        import app1.repository.GameDAO;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.runners.MockitoJUnitRunner;

        import java.util.Map;

        import static org.hamcrest.CoreMatchers.is;
        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.mockito.Mockito.verify;
        import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private GameDAO gameDAO;

    private Move move;
    private GameEntity gameEntity;

    @Before
    public void setup() {
        move = new Move();
        gameEntity = new GameEntity();
        gameEntity.setComputerSymbol('O');
        gameEntity.setUserSymbol('X');
    }
    @Test // czy taki test ma sens ?
    public void shouldCallDAOMethod() {
        boardService.isBoardCellAvailable(move);
        verify(gameDAO).isMovePossible(move);
    }

    @Test
    public void shouldChangeCurrentPlayerToUserAndUpdateGame() {
        gameEntity.setCurrentPlayingSymbol(gameEntity.getComputerSymbol());
        boardService.changePlayer(gameEntity);
        assertThat(gameEntity.getCurrentPlayingSymbol(), is(gameEntity.getUserSymbol()));
        verify(gameDAO).updateGame(gameEntity);
    }
    @Test
    public void shouldChangeCurrentPlayerToComputerAndUpdateGame() {
        gameEntity.setCurrentPlayingSymbol(gameEntity.getUserSymbol());
        boardService.changePlayer(gameEntity);
        assertThat(gameEntity.getCurrentPlayingSymbol(), is(gameEntity.getComputerSymbol()));
        verify(gameDAO).updateGame(gameEntity);
    }
}
