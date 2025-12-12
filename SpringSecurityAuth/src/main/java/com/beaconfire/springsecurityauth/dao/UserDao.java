package com.beaconfire.springsecurityauth.dao;



import com.beaconfire.springsecurityauth.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractHibernateDao<User> {

    public UserDao() {
        setClazz(User.class);
    }


    public User findByUsername(String username) {

        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.select(root).where(cb.equal(root.get("username"), username));

        User result = session.createQuery(query).uniqueResult();

        return result;
    }

    public boolean existsByUsername(String username) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        query.select(cb.count(root));
        query.where(cb.equal(root.get("username"), username));

        Long count = session.createQuery(query).uniqueResult();
        return count != null && count > 0;
    }

    public boolean existsByEmail(String email) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        query.select(cb.count(root));
        query.where(cb.equal(root.get("email"), email));

        Long count = session.createQuery(query).uniqueResult();
        return count != null && count > 0;
    }


    public void saveUser(User user) {
        this.save(user);
    }
}


