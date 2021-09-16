package com.example.recipes.controller;

import com.example.recipes.exceptions.UserAlreadyExistsException;
import com.example.recipes.model.User;
import com.example.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "register")
    @ResponseStatus(value = HttpStatus.OK)
    public void registerUser(@RequestBody @Valid User user) {

        if (userService.isUserAlreadyExists(user)) {
            throw new UserAlreadyExistsException();
        } else {
            userService.save(user);
        }
    }
}