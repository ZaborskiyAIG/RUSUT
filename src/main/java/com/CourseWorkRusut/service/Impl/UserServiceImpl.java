package com.CourseWorkRusut.service.Impl;

import com.CourseWorkRusut.DAO.UserDAO;
import com.CourseWorkRusut.DTO.StudentDTO;
import com.CourseWorkRusut.DTO.TeacherDTO;
import com.CourseWorkRusut.DTO.UserDTO;
import com.CourseWorkRusut.mappers.UserMapper;
import com.CourseWorkRusut.model.*;
import com.CourseWorkRusut.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserDAO userDAO ;

    private UserMapper userMapper;

    private StudentService studentService;

    private RoleService roleService;

    private TeacherService teacherService;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserDAO userDAO, UserMapper userMapper, TeacherService teacherService, StudentService studentService, RoleService roleService){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDAO = userDAO;
        this.userMapper = userMapper;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void register(User user) {
        Role role = roleService.getRoleByName("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(role);
        userDAO.save(user);
    }


    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {   //что происходит, когда из одного метода помеченым @транзакция вызывают другой метод c @транзакция

        User user = userDAO.getUserById(userDTO.getUserId());       //метод работает так, что в конце вернет либо модифайнд, либо юзера, надо пофиксить
                                                                    //либо сохраняет модифайнд, либо обновляет юзера, надо пофиксить
        User modifiedUser =  userMapper.userDTOToUser(userDTO);    //предпологается, что вместе с модифайндом придет намббербук, надо проверить
        modifiedUser.setLogin(user.getLogin());
        modifiedUser.setPassword(user.getPassword());
        modifiedUser.setRole(roleService.getRoleByName(userDTO.getNameRole())); //чекнуть почему именно так

        if(userDTO.getClass() == StudentDTO.class) {
         modifiedUser = studentService.updateStudent((Student) modifiedUser);
        }

        if(userDTO.getClass() == TeacherDTO.class) {
            modifiedUser = teacherService.updateTeacher((Teacher) modifiedUser);
        }

        if(!user.getRole().getNameRole().equals(modifiedUser.getRole().getNameRole())){
            userDAO.delete(user);
            modifiedUser.setUserId(null);
            userDAO.save(modifiedUser);
        }

        userDAO.update(modifiedUser);

        return userMapper.userToUserDTO(modifiedUser);
    }


    @Override
    @Transactional
    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = new User();
        user.setUserId(id);
        userDAO.delete(user);
    }

    @Override
    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userDAO.getUserById(id);
        return userMapper.userToUserDTO(user);
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    @Transactional
    public Map<String, Object> getAllUser(String offset) {

        Long count = userDAO.contUsers("ROLE_USER");
        List list = userDAO.getAllUser(offset);

        Map<String, Object> map = new HashMap<>();
        map.put("count",count);
        map.put("users",list);

        return map;
    }

    @Override
    @Transactional
    public Long contUsers(String nameRole) {
        nameRole = convertRoles(nameRole);
        return userDAO.contUsers(nameRole);
    }

    private String convertRoles(String nameRole){
        if(nameRole.equals("students") || nameRole.equals("student"))
            return "ROLE_STUDENT";

        if(nameRole.equals("teachers") || nameRole.equals("teacher"))
            return "ROLE_TEACHER";

        if(nameRole.equals("admins") || nameRole.equals("admin"))
            return "ROLE_ADMIN";

        return "ROLE_USER";
    }

    @Override
    @Transactional
    public Map<String, Object> searchUsers(String search) {  //чет хуита какаита
         Long count = userDAO.contUsers("ROLE_USER");
         List list = userDAO.searchUsersByWords(search);

         Map<String, Object> map = new HashMap<>();
         map.put("count",count);
         map.put("users",list);

        return map;
    }

}
