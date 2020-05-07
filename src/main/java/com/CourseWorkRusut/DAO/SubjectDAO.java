package com.CourseWorkRusut.DAO;

import com.CourseWorkRusut.model.Subject;

import java.util.List;

public interface SubjectDAO {

    Subject getSubjectByName(String nameSubject);

    List<Subject> getSubjectByTeacher(Long idTeacher);

}
