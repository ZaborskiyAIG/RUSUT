package com.CourseWorkRusut.controller;


import com.CourseWorkRusut.DTO.StudentDTO;
import com.CourseWorkRusut.DTO.UserDTO;
import com.CourseWorkRusut.model.Specialty;
import com.CourseWorkRusut.model.User;
import com.CourseWorkRusut.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class TestController {

    @Autowired
    UserService userService;

    @Autowired
    PositionScienceDegreeService positionScienceDegreeService;

    @Autowired
    SpecialtyService specialtyService;

    @Autowired
    StudyGroupService studyGroupService;

    @Autowired
    RoleService roleService;

//    @GetMapping(value = "/s/{id}")
//    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
//
//
//
//        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/s")
//    public ResponseEntity<List<UserDTO>> getAllStudents(@RequestParam(value = "offset", defaultValue = "0" )String offset,
//                                                        @RequestParam(required = false) Long specialtyId,
//                                                        @RequestParam(required = false) Long groupId) {
//        return new ResponseEntity<>(userService.getStudentsByParameters(offset, groupId, specialtyId), HttpStatus.OK);
//    }

//    @PostMapping(value = "/s")
//    public ResponseEntity updateUser(@RequestBody UserDTO userDTO ){
//        userService.update(userDTO);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @PutMapping(value = "/s")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO ){

        System.out.println(userDTO.getClass() == StudentDTO.class);
        System.out.println(userDTO.getClass() == UserDTO.class);
        System.out.println("role"+userDTO.getNameRole());

        return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
    }


}
