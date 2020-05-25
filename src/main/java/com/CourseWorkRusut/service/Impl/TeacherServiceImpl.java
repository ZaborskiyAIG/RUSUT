package com.CourseWorkRusut.service.Impl;

import com.CourseWorkRusut.DAO.*;
import com.CourseWorkRusut.DTO.*;
import com.CourseWorkRusut.mappers.UserMapper;
import com.CourseWorkRusut.model.*;
import com.CourseWorkRusut.service.StudentService;
import com.CourseWorkRusut.service.StudyGroupService;
import com.CourseWorkRusut.service.SubjectService;
import com.CourseWorkRusut.service.TeacherService;

import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private PositionScienceDegreeDAO positionScienceDegreeService;

    private UserMapper userMapper;

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private ExamDAO examDAO;

    @Autowired
    private UserDAO userDAO;

    private TeacherDAO teacherDAO;

    @Autowired
    public TeacherServiceImpl(PositionScienceDegreeDAO positionScienceDegreeService, UserMapper userMapper, TeacherDAO teacherDAO){
        this.positionScienceDegreeService =  positionScienceDegreeService;
        this.userMapper = userMapper;
        this.teacherDAO = teacherDAO;
    }

    @Transactional
    public User updateTeacher(Teacher teacher){

        List<String> namePositions = new ArrayList<>();
        for(Position position : teacher.getPositions()){
            namePositions.add(position.getNamePosition());
        }

        List<String> nameScienceDegrees = new ArrayList<>();
        for(ScienceDegree scienceDegree : teacher.getScienceDegrees()){
            nameScienceDegrees.add(scienceDegree.getNameScienceDegree());
        }

        teacher.setPositions(new HashSet<>(positionScienceDegreeService.getPositionsByName(namePositions)));
        teacher.setScienceDegrees(new HashSet<>(positionScienceDegreeService.getScienceDegreeByName(nameScienceDegrees)));

        return teacher;
    }




    @Override
    @Transactional
    public UserCounterDTO getTeachersByParameters(String offset,String position, String degree) {
        List<User> users = teacherDAO.getTeachersByParameters(offset, position, degree);

        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(userMapper.userToUserDTO(user));
        }

        Long counter = teacherDAO.counterTeachersByParameters(position, degree);

        return new UserCounterDTO(userDTOS,counter);
    }

    @Override
    @Transactional
    public List<SubjectTeacherGroupDTO> getSubjectTeacherGroupDTO(Long teacherId) {
        List<SubjectTeacherGroupDTO> list = new ArrayList<>();

        Set<Subject> subjects = new HashSet<>(subjectService.getSubjectByTeacher(teacherId));

        for(Subject sb : subjects){

            List<StudyGroup> studyGroups = studyGroupService.getStudyGroupBySubject(sb.getNameSubject(), teacherId);

            List<String> numberStudyGroup = new ArrayList<>();
            for(StudyGroup StudyGroup : studyGroups){
                numberStudyGroup.add(StudyGroup.getNumberGroup());
            }


            List<String> sem = studentDAO.getSemesterByExam(teacherId, sb.getNameSubject());

            Set<String> set = new HashSet<>(sem);

            for(String semes: set){
                SubjectTeacherGroupDTO subjectTeacherGroupDTO = new SubjectTeacherGroupDTO();

                subjectTeacherGroupDTO.setGroups(numberStudyGroup);
                subjectTeacherGroupDTO.setSubject(sb.getNameSubject());

                List<String> s = new ArrayList<>();

                s.add(semes);

                subjectTeacherGroupDTO.setSemesters(s);
                list.add(subjectTeacherGroupDTO);
            }


        }
        return list;
    }

    @Override
    @Transactional
    public SubjectTeacherGroupDTO updateSubjectTeacherGroup(SubjectTeacherGroupDTO stg, Long id) {

    //    List<SubjectTeacherGroup> subjectTeacherGroups = new ArrayList<>();

        Teacher teacher = (Teacher) userDAO.getUserById(id);       //метод работает так, что в конце вернет либо модифайнд, либо юзера, надо пофиксить

        //    for (SubjectTeacherGroupDTO ss : stg) {
                for (String str : stg.getGroups()) {
                    SubjectTeacherGroup s = new SubjectTeacherGroup();
                    StudyGroup studyGroup = studyGroupService.getStudyGroupByNumberGroup(str);
                    s.setStudyGroup(studyGroup);

                    Subject subject = subjectService.getSubjectByName(stg.getSubject());

                    s.setSubject(subject);
                    s.setTeacher(teacher);
                    teacherDAO.saveSubjectTeacherGroup(s);

             //       subjectTeacherGroups.add(s);

                    List<StudentExamDTO> list = studentService.getStudentsByNumberGroup(str);

                    for(StudentExamDTO dto: list){
                        System.out.println("пройтись по листу и получить всех его студентов:"+dto.getUserId());
                    }

                    for(StudentExamDTO dto: list){
                        System.out.println("ID:"+dto.getUserId());
                                                                                                                //я буду себя люто не навижеть за эти строчки, особенно, когда
                                                                                                                // буду фиксить все это говно, чтобы залить на гитхаб как портфолио, прости будущий я
                        List<Semester> semester =  studentDAO.getSemesterByUserAndAmountSemester(dto.getUserId(),stg.getSemesters());

                        for(Semester sem: semester){
                            System.out.println("EXAM:"+sem.getSemesterId());
                            Exam exam = new Exam();
                            exam.setSemester(sem);
                            exam.setSubject(subject);
                            exam.setTeacher(teacher);
                            examDAO.save(exam);
                        }
                    }
                }
          //  }

        return stg;
    }

    @Override
    @Transactional
    public void deleteSubjectTeacherGroup(SubjectTeacherGroupDTO dto, Long teacherId) {

        Subject subject = subjectService.getSubjectByName(dto.getSubject());

        for(String group:  dto.getGroups()) {
            StudyGroup studyGroup = studyGroupService.getStudyGroupByNumberGroup(group);

            List<SubjectTeacherGroup> list = teacherDAO.getSTGByTeacherId(teacherId, subject.getSubjectId(), studyGroup.getGroupId());

            for (SubjectTeacherGroup ss : list) {
                teacherDAO.deleteSubjectTeacherGroup(ss);
            }
        }
    }

    @Override
    @Transactional
    public List<TeacherNameDTO> getFullNameTeachers() {
        return teacherDAO.getFullNameTeachers();
    }

    @Override
    @Transactional
    public UserCounterDTO searchTeacherByFullName(String search) {
        Long count = teacherDAO.counterTeacherByFullName(search);
        List<User> users = teacherDAO.searchTeacherByFullName(search.replace("+", " "));

        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(userMapper.userToUserDTO(user));
        }

        return new UserCounterDTO(userDTOS,count );
    }


//    @Override
//    @Transactional
//    public void deleteSubjectTeacherGroup(List<SubjectTeacherGroupDTO> subjectTeacherGroupDTO) {
//
//        for(SubjectTeacherGroupDTO dto: subjectTeacherGroupDTO) {
//            List<SubjectTeacherGroup> subjectTeacherGroup = teacherDAO.getSubjectTeacherGroupByNumberGroupBySubject(dto.getGroups(),dto.getSubject() );
//
//               for(SubjectTeacherGroup stg:subjectTeacherGroup)
//                   teacherDAO.deleteSubjectTeacherGroup(stg);
//        }
//    }


//    public List<ExamGroupDTO> getExamGroup(Long teacherId){
//
//    //    List<StudentExamDTO> list = teacherDAO.getExamBy
//
//        return null;
//    }


}
