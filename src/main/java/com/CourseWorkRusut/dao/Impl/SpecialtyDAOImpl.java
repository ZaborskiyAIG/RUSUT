package com.CourseWorkRusut.dao.Impl;

import com.CourseWorkRusut.dao.SpecialtyDAO;
import com.CourseWorkRusut.model.Specialty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpecialtyDAOImpl implements SpecialtyDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public SpecialtyDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Specialty getSpecialtyById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.load(Specialty.class,id);
    }

    @Override
    public Specialty getSpecialtyByName(String nameSpecialty) {
        Session session = sessionFactory.getCurrentSession();
        Query<Specialty> query = session.createQuery("from Specialty specialty where specialty.nameSpecialty =:nameSpecialty", Specialty.class);
        query.setParameter("nameSpecialty", nameSpecialty);
        return query.uniqueResult();

    }

    @Override
    public List<String> getAllSpecialty() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select specialty.nameSpecialty  FROM Specialty specialty ", String.class).list();
    }

    @Override
    public Integer getAmountSpecialty(String specialty) {
        Session session = sessionFactory.getCurrentSession();
        Query<Integer> query = session.createQuery("select specialty.amountSemester  FROM Specialty specialty where specialty.nameSpecialty =:nameSpecialty", Integer.class);
        query.setParameter("nameSpecialty",specialty);
        return query.uniqueResult();
    }

}
