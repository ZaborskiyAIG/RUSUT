package com.CourseWorkRusut.dao;

import com.CourseWorkRusut.model.Position;
import com.CourseWorkRusut.model.ScienceDegree;

import java.util.List;

public interface PositionScienceDegreeDAO {

    List<String> getAllPositions();

    List<String> getAllScienceDegree();

    List<Position> getPositionsByName(List<String> namePosition);

    List<ScienceDegree> getScienceDegreeByName(List<String> nameScienceDegree);
}
