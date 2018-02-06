package app1.service;

        import app1.model.GameEntity;
        import app1.model.Move;
        import app1.repository.GameDAO;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.runners.MockitoJUnitRunner;
        import static org.hamcrest.CoreMatchers.is;
        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.mockito.Mockito.verify;



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
    }
    @Test // czy taki test ma sens ?
    public void shouldCallDAOMethod() {
        boardService.isBoardCellAvailable(move);
        verify(gameDAO).isMovePossible(move);
    }
    @Test
    public void shouldChangePlayerAndUpdateGame() {
        gameEntity.setUserNextMove(true);
        boardService.changePlayer(gameEntity);
        assertThat(gameEntity.isUserNextMove(), is(false));
        verify(gameDAO).updateGame(gameEntity);
    }
}
