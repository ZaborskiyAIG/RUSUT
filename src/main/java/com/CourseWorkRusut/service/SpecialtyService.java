package com.CourseWorkRusut.service;

import com.CourseWorkRusut.model.Specialty;

import java.util.List;

public interface SpecialtyService {

    Specialty getSpecialtyById(Long id);

    Specialty getSpecialtyByName(String nameSpecialty);

    List<Specialty> getAllSpecialty();
}