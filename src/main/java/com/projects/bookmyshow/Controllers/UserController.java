package com.projects.bookmyshow.Controllers;

import com.projects.bookmyshow.DTO.ResponseStatus;
import com.projects.bookmyshow.DTO.SignUpRequestDTO;
import com.projects.bookmyshow.DTO.SignUpResponseDTO;
import com.projects.bookmyshow.Models.User;
import com.projects.bookmyshow.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO){
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        try{
            User user = userService.signUp(signUpRequestDTO.getEmail(),signUpRequestDTO.getPassword());
            signUpResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            signUpResponseDTO.setUserId(user.getId());
        }
        catch(Exception e){
            signUpResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return signUpResponseDTO;
    }
}
