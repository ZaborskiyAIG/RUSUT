package com.CourseWorkRusut.dao.Impl;

import com.CourseWorkRusut.dao.LibraryDAO;
import com.CourseWorkRusut.dto.LibraryDTO;
import com.CourseWorkRusut.model.Author;
import com.CourseWorkRusut.model.Library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class LibraryDAOImpl implements LibraryDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public LibraryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Library getLibraryById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Library.class,id);
    }

    @Override
    public void updateLibrary(Library library) {
        Session session = sessionFactory.getCurrentSession();
        session.update(library);
    }

    @Override
    public Long countLibrary(){
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) From Library library where library.libraryId in (select library.libraryId From Library library join library.authors aut)");
        return (Long)  query.uniqueResult();
    }



    @Override
    public void save(Library library) {
        Session session = this.sessionFactory.getCurrentSession();

        for (Author author : library.getAuthors()) {
            System.out.println("sss"+author.getName());
            session.save(author);
        }

        session.save(library);
    }

    @Override
    public void delete(Library library) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(library);
    }

    @Override
    @Deprecated
    public List getLibraryBySearch(String offset, String search) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "select library From Library library join fetch library.authors aut where library.name  LIKE :words")
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    Set<LibraryDTO> set = new LinkedHashSet<>();

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        Long id = ((Library)objects[0]).getLibraryId();
                        String name = ((Library)objects[0]).getName();
                        LocalDate localDate = ((Library)objects[0]).getData();

                        List<String> list = new ArrayList<>();
                        for(Author author: ((Library)objects[0]).getAuthors()) {

                            if (author.getMiddlename() == null)
                                author.setMiddlename("");

                            list.add(author.getSurname() +" " + author.getName() + " " + author.getMiddlename());
                        }

                        LibraryDTO libraryDTO = new LibraryDTO(id,name,localDate,list);

                        set.add(libraryDTO);

                        return libraryDTO;
                    }

                    @Override
                    public List transformList(List list) {
                        return new ArrayList<>(set);
                    }
                });

        query.setFirstResult(Integer.valueOf(offset));
        query.setMaxResults(Integer.valueOf(offset)+25);
        query.setParameter("words", "%"+search+"%");

        return query.getResultList();
    }

    @Override
    public Long countLibraryBySearch(String search) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) From Library library where library.libraryId in (select library.libraryId From Library library join library.authors aut where library.name  LIKE :words)");
        query.setParameter("words", "%"+search+"%");
        return (Long)  query.uniqueResult();
    }

    @Override
    @Deprecated
    public List<LibraryDTO> getAllLibrary(String offset) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "select library From Library library join fetch library.authors aut")
        .unwrap(Query.class)
        .setResultTransformer(new ResultTransformer() {

            Set<LibraryDTO> set = new LinkedHashSet<>();

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                Long id = ((Library)objects[0]).getLibraryId();
                String name = ((Library)objects[0]).getName();
                LocalDate localDate = ((Library)objects[0]).getData();

                List<String> list = new ArrayList<>();
                for(Author author: ((Library)objects[0]).getAuthors()) {

                    if (author.getMiddlename() == null)
                        author.setMiddlename("");

                    list.add(author.getSurname() +" " + author.getName() + " " + author.getMiddlename());
                }

                LibraryDTO libraryDTO = new LibraryDTO(id,name,localDate,list);

                set.add(libraryDTO);

                return libraryDTO;
            }

            @Override
            public List transformList(List list) {
                return new ArrayList<>(set);
            }
        });

        query.setFirstResult(Integer.valueOf(offset));
        query.setMaxResults(Integer.valueOf(offset)+25);

        return query.getResultList();
    }
}

























