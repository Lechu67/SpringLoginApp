package app1.repository;


import app1.model.UserEntity;
import app1.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;


import java.util.List;


@Repository
@Transactional/*(propagation = Propagation.MANDATORY)*/
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void insert(UserEntity userEntity) {
        getSession().save(userEntity);
        for (UserRole userRole : userEntity.getRoles()) {
            getSession().save(new UserRole(userRole.getRole(), userEntity));
        }
    }
    @Override
    public UserEntity findByName(String username) {
        List<UserEntity> user = getSession()
                .createQuery("from UserEntity where username=?")
                .setParameter(0,username)
                .list();
        return user.size() > 0 ? user.get(0) : null;
    }
    private Session getSession(){
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
        return sessionFactory.getCurrentSession();
    }
}

