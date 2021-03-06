package com.CourseWorkRusut.dao;

import com.CourseWorkRusut.model.StudyGroup;

import java.util.List;

public interface StudyGroupDAO {

    Long addStudyGroup(StudyGroup studyGroup);

    List<String> getAllStudyGroupByNameSpecialty(String nameSpecialty);

    Long getCountStudentsInGroup(String numberGroup);

    StudyGroup getStudyGroupById(Long id);

    StudyGroup getStudyGroupByNumberGroup(String numberGroup);

    List<StudyGroup> getStudyGroupBySubject(String nameSubject, Long teacherId);
}
