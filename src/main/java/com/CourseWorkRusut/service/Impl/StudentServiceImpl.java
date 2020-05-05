package com.CourseWorkRusut.service.Impl;

import com.CourseWorkRusut.DAO.StudentDAO;
import com.CourseWorkRusut.DTO.UserCounterDTO;
import com.CourseWorkRusut.DTO.UserDTO;
import com.CourseWorkRusut.model.Student;
import com.CourseWorkRusut.model.StudyGroup;
import com.CourseWorkRusut.model.User;
import com.CourseWorkRusut.service.StudentService;
import com.CourseWorkRusut.service.StudyGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudyGroupService studyGroupService;

    private StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudyGroupService studyGroupService, StudentDAO studentDAO) {
        this.studyGroupService = studyGroupService;
        this.studentDAO = studentDAO;
    }

    @Override
    @Transactional
    public User updateStudent(Student student){

        if(student.getNumberBook() ==null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String entryYear = student.getEntryDate().format(formatter) ;

            String nameSpecialty = student.getStudyGroup().getSpecialty().getNameSpecialty();

            StudyGroup studyGroup = studyGroupService.getStudyGroupForAddStudent(nameSpecialty, entryYear);
            student.setStudyGroup(studyGroup);

            student.setNumberBook(generationNumberStudyBook(entryYear, student.getStudyGroup()));

            return student;
        }

        StudyGroup studyGroup = studyGroupService.getStudyGroupByNumberGroup(student.getStudyGroup().getNumberGroup());
        student.setStudyGroup(studyGroup);

        return student;
    }


    @Transactional
    public String generationNumberStudyBook(String entryDate, StudyGroup studyGroup) {

        System.out.println("А?"+entryDate);

        Long number = studyGroupService.getCountStudentsInGroup(studyGroup) +1;

        String numberStudent = number < 10 ? "0"+number : String.valueOf(number);

        return entryDate.substring(entryDate.length()-4) +
                "0" +
                studyGroup.getNumberGroup().charAt(studyGroup.getNumberGroup().length()-1) +
                numberStudent;

    }


    @Override
    @Transactional
    public UserCounterDTO getStudentsByParameters(String offset, String group, String specialty) {
        Long count = studentDAO.counterStudentsByParameters(group, specialty);
        List<UserDTO> userDTOS = studentDAO.getStudentsByParameters(offset, group, specialty);

        return new UserCounterDTO(userDTOS,count );
    }

    @Override
    @Transactional
    public UserCounterDTO searchStudentByFullName(String search) {
        Long count = studentDAO.counterStudentsByFullName(search);
        List<UserDTO> userDTOS = studentDAO.searchStudentByFullName(search);

        return new UserCounterDTO(userDTOS,count );
    }
}
