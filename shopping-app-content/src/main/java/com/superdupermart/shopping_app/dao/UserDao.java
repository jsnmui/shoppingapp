package com.superdupermart.shopping_app.dao;



import com.superdupermart.shopping_app.entity.User;
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

    public User findByUserName(String username) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.select(root).where(cb.equal(root.get("username"), username));

        return session.createQuery(query).uniqueResult();
    }
}
