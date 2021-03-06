package com.CourseWorkRusut.service;

import com.CourseWorkRusut.model.Position;
import com.CourseWorkRusut.model.ScienceDegree;

import java.util.List;

public interface PositionScienceDegreeService {

    List<String> getAllPositions();

    List<String> getAllScienceDegree();

    List<Position> getPositionsByName(List<String> namePosition);

    List<ScienceDegree> getScienceDegreeByName(List<String> nameScienceDegree);

}
