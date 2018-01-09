package app1.repository;

import app1.model.GameEntity;
import app1.model.Move;
import app1.model.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//@Repository
public class GameDAOImpl implements GameDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean isMoveExists(Move move) {
        return false;
    }

    @Override
    public List<Move> findMovesByGameId(int gameId) {
        List<Move> moves = sessionFactory.getCurrentSession()
                .createQuery("from Move where game_id=?")
                .setParameter(0,gameId)
                .list();
        return moves.size() > 0 ? moves : null;
    }


    @Override
    public void saveNewMove(Move move) {
        sessionFactory.getCurrentSession().save(move);
    }

    @Override
    public void saveNewGame(GameEntity gameEntity) {
        sessionFactory.getCurrentSession().save(gameEntity);
    }

    @Override
    public GameEntity findGameByUserName(String userName) {
        return null;
    }


}
