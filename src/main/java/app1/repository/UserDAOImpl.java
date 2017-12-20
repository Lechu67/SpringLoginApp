package app1.repository;


import app1.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void insert(UserEntity userEntity) {
        getSession().save(userEntity);
    }
    @Override
    public UserEntity findByName(String username) {

        List<UserEntity> user = getSession()
                .createQuery("from UserEntity where username=?")
                .setParameter(0,username)
                .list();
        return user.size() > 0 ? user.get(0) : null;
    }
 /*   public List<String> getUserRoles(String username){
        List<String> roles = getSession()
                .createQuery("select role from UserRole where username=?")
                .setParameter(0, username)
                .list();
        return roles;
    }*/
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}

