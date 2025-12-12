package com.superdupermart.shopping_app.service;



import com.superdupermart.shopping_app.dao.UserDao;
import com.superdupermart.shopping_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Transactional(readOnly = true)
    public User getCurrentAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return getUserByUsername(username);
    }
}
