package com.CourseWorkRusut.service;

import com.CourseWorkRusut.DTO.InternshipDTO;
import com.CourseWorkRusut.model.Internship;
import com.CourseWorkRusut.model.LearningActivities;
import com.CourseWorkRusut.model.PlacePractice;

import java.util.List;

public interface InternshipService {

    List<InternshipDTO> getAllInternships();

    void delete(Long id);

    void deletePlace(Long id);

    void save(Internship internship);

    void save(PlacePractice placePractice);

    List<PlacePractice> getAllPlace(String offset);

    List<PlacePractice> getAllPlace();

    PlacePractice getPlace(Long id);

    void updatePlace(PlacePractice placePractice);

    Long counterPlace();

    List<Internship> getInternshipsByStudent(Long id);

    List<LearningActivities> getLearningsByStudent(Long id);

}
