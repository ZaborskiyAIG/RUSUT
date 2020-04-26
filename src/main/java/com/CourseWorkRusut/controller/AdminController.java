package com.CourseWorkRusut.controller;

import com.CourseWorkRusut.DTO.UserDTO;
import com.CourseWorkRusut.model.Specialty;
import com.CourseWorkRusut.model.User;
import com.CourseWorkRusut.service.SpecialtyService;
import com.CourseWorkRusut.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private UserService userService;

    private SpecialtyService specialtyService;


    @Autowired
    public AdminController(UserService userService, SpecialtyService specialtyService) {
        this.userService = userService;
        this.specialtyService = specialtyService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDTO>> getAllUser(@RequestParam(value = "offset", defaultValue = "0" )String offset) {
        return new ResponseEntity<>(userService.getAllUser(offset), HttpStatus.OK);
    }

    @GetMapping(value = "/students")
    public ResponseEntity<List<UserDTO>> getAllStudents(@RequestParam(value = "offset", defaultValue = "0" )String offset,
                                                                   @RequestParam(required = false) Long specialtyId,
                                                                   @RequestParam(required = false) Long groupId) {
        return new ResponseEntity<>(userService.getStudentsByParameters(offset, groupId, specialtyId), HttpStatus.OK);
    }

    @GetMapping(value = "/teachers")
    public ResponseEntity<List<UserDTO>> getAllTeacher(@RequestParam(value = "offset", defaultValue = "0" )String offset) { //requestBody? HttpServletRequest? чек поле consumer
        return new ResponseEntity<>(userService.getTeachersByParameters(offset), HttpStatus.OK);
    }

    @GetMapping(value = "/counterUsers")
    public ResponseEntity<Map<String,Long>> counterUser() {

        Map<String,Long> map =  new HashMap<>();
        map.put("counter",userService.contUsers());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/users/updateUser")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO ){
        userService.update(userDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/users/updateUsers")
    public ResponseEntity updateUsers(@RequestBody List<User> users){
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/addUser")
    public ResponseEntity addUser(@RequestBody User user) {

        if(userService.getUserByLogin(user.getLogin())!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Such user already exists");
        }
        userService.register(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/specialties")
    public ResponseEntity<List<Specialty>> allSpecialties() {
        return new ResponseEntity<>(specialtyService.getAllSpecialty(), HttpStatus.OK);
    }


}