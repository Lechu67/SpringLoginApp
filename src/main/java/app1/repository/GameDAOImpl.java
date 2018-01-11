package app1.repository;

import app1.model.GameEntity;
import app1.model.Move;
import app1.model.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameDAOImpl implements GameDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean isMovePossible(Move move) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Move where x=?0, y=?1 and game_id=?2")
                .setParameter(0,move.getColumn())
                .setParameter(1,move.getRow())
                .setParameter(2,move.getGameID())
                .list()
                .isEmpty();
    }

    @Override
    public List<Move> findMovesByGameId(int gameId) {
        List<Move> moves = sessionFactory.getCurrentSession()
                .createQuery("from Move where game_id=?")
                .setParameter(0,gameId)
                .list();
        return moves;
//        return moves.size() > 0 ? moves : null;
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
        List<GameEntity> gameEntity = sessionFactory.getCurrentSession()
                .createQuery("from GameEntity where username=?")
                .setParameter(0,userName)
                .list();
        return gameEntity.size() > 0 ? gameEntity.get(0) : null;
    }


}
