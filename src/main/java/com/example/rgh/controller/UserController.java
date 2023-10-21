package com.example.rgh.controller;

import com.example.rgh.dto.UserDTO;
import com.example.rgh.entity.User;
import com.example.rgh.facade.UserFacade;
import com.example.rgh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFacade userFacade;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal) {
//        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);

        UserDTO userUpdated = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
