package com.CourseWorkRusut.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "Student_id")
public class Student extends User {

   // @Id
   // @Column(name = "Student_id")
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  private long studentId;

    @Column(name = "number_book")
    private long numberBook;

    @Column(name = "entry_year")
    private LocalDate entryDate = LocalDate.now();

    @ManyToOne
    @JoinColumn (name="Group_id")
    private StudyGroup studyGroup;

    @OneToMany(mappedBy = "student", fetch=FetchType.LAZY)
    private List<Semester> semester;

    public long getNumberBook() {
        return numberBook;
    }

    public void setNumberBook(long numberBook) {
        this.numberBook = numberBook;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public List<Semester> getSemester() {
        return semester;
    }

    public void setSemester(List<Semester> semester) {
        this.semester = semester;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    @JsonIgnore
    public LocalDate getEntryYear() {
        return entryDate;
    }

    public void setEntryYear(LocalDate entryYear) {
        this.entryDate = entryYear;
    }

}
