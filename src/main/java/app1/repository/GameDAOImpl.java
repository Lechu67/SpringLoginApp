package app1.repository;

import app1.model.GameEntity;
import app1.model.Move;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

//@Repository
public class GameDAOImpl implements GameDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean isMoveExists(Move move) {
        return false;
    }

    @Override
    public void saveNewGame(GameEntity gameEntity) {
        sessionFactory.getCurrentSession().save(gameEntity);
    }
}
