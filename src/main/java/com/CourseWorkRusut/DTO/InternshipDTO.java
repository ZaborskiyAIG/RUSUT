package com.CourseWorkRusut.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class InternshipDTO {

    private Long internshipId;

    private String director;

    private String numberSemester;

    private String nameTeacher;

    private String mark;

    private Long userId;

    private Long placePracticeId;

    private String topic;

    public InternshipDTO(Long internshipId,
                         String director,
                         String numberSemester,
                         String nameTeacher,
                         String mark,
                         Long userId,
                         Long placePracticeId,
                         String topic) {
        this.internshipId = internshipId;
        this.director = director;
        this.numberSemester = numberSemester;
        this.nameTeacher = nameTeacher;
        this.mark = mark;
        this.userId = userId;
        this.placePracticeId= placePracticeId;
        this.topic = topic;
    }

    public InternshipDTO() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlacePracticeId() {
        return placePracticeId;
    }

    public void setPlacePracticeId(Long placePracticeId) {
        this.placePracticeId = placePracticeId;
    }

    public Long getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Long internshipId) {
        this.internshipId = internshipId;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getNumberSemester() {
        return numberSemester;
    }

    public void setNumberSemester(String numberSemester) {
        this.numberSemester = numberSemester;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}