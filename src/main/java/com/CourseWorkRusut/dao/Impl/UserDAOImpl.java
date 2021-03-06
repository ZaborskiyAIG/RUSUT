package com.CourseWorkRusut.dao.Impl;

import com.CourseWorkRusut.dao.UserDAO;
import com.CourseWorkRusut.dto.UserDTO;
import com.CourseWorkRusut.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;


    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Long) session.save(user);
    }

    @Override
    public void update(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.merge(user);
    }

    @Override
    public void delete(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(user);

    }


    @Override
    public User getUserByLogin(String login) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where login=:login");
        query.setParameter("login", login);

       return (User) query.uniqueResult();
    }

    @Override
    public User getUserById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(User.class,id);
    }


    @Override
    public User getUserByEmail(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where email=:email");
        query.setParameter("email", email);

        return (User) query.uniqueResult();
    }

    @Override
    public List<UserDTO> getAllUser(String offset) {

        Session session = this.sessionFactory.getCurrentSession();
        Query<UserDTO> query = session.createQuery(" select new com.CourseWorkRusut.dto.UserDTO(user.userId, user.name,user.surname,user.middlename,user.email, user.role.nameRole) From User user where type(user) in :types", UserDTO.class);
        query.setParameter("types", User.class);
        query.setFirstResult(Integer.valueOf(offset));
        int quantityUsersForPagination = 25;
        query.setMaxResults(Integer.valueOf(offset)+ quantityUsersForPagination);

        return query.list();
    }

    @Override
    public Long contUsers(String nameRole){
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count (user.userId) from User user where user.role.nameRole=:nameRole");
        query.setParameter("nameRole",nameRole);
        return (Long)  query.uniqueResult();
    }

    @Override
    public Long contUsersByFullName(String words){
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(" select count (user.userId)" +
                "from User user where (type(user) in :types) and " +
                "((concat(user.name,' ',user.surname,' ', IFNULL(user.middlename,'') ) LIKE :words) or " +
                "(concat(user.name,' ', IFNULL(user.middlename,''),' ',user.surname) LIKE :words) or " +
                "(concat(user.surname,' ',IFNULL(user.middlename,''),' ',user.name) LIKE :words) or" +
                "(concat(user.surname,' ',user.name,' ',IFNULL(user.middlename,'')) LIKE :words) or" +
                "(concat(IFNULL(user.middlename,''),' ',user.name,' ',user.surname) LIKE :words) or" +
                "(concat(IFNULL(user.middlename,''),' ',user.surname,' ',user.name) LIKE :words) )");

        query.setParameter("words","%"+words+"%");
        query.setParameter("types", User.class);
        return (Long)query.uniqueResult();
    }

    @Override
    public List<UserDTO> searchUsersByWords(String words) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(" select new com.CourseWorkRusut.dto.UserDTO(user.userId, user.name, user.surname, user.middlename, user.email, user.role.nameRole) " +
                "from User user where (type(user) in :types) and " +
                "((concat(user.name,' ',user.surname,' ', IFNULL(user.middlename,'') ) LIKE :words) or " +
                "(concat(user.name,' ', IFNULL(user.middlename,''),' ',user.surname) LIKE :words) or " +
                "(concat(user.surname,' ',IFNULL(user.middlename,''),' ',user.name) LIKE :words) or" +
                "(concat(user.surname,' ',user.name,' ',IFNULL(user.middlename,'')) LIKE :words) or" +
                "(concat(IFNULL(user.middlename,''),' ',user.name,' ',user.surname) LIKE :words) or" +
                "(concat(IFNULL(user.middlename,''),' ',user.surname,' ',user.name) LIKE :words) )");

        query.setParameter("words","%"+words+"%");
        query.setParameter("types", User.class);

        return query.list();
    }



}
