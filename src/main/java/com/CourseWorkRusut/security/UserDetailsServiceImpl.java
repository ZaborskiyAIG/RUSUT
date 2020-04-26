package com.CourseWorkRusut.security;

import com.CourseWorkRusut.model.User;
import com.CourseWorkRusut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login)  { //проверить без throws
        User user = userService.getUserByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + login + " not found");
        }

        return user;
    }
}