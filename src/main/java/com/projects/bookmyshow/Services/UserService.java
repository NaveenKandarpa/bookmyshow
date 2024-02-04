package com.projects.bookmyshow.Services;

import com.projects.bookmyshow.Models.User;
import com.projects.bookmyshow.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User signUp(String email, String password){
        // 1. validate if user already exists
        Optional<User> userOptional = userRepository.findbyEmail(email);
        if(userOptional.isPresent()){
            throw new RuntimeException("User already present!!");
        }

        User user = new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setBookings(new ArrayList<>());
        user.setEmail(email);
        user = userRepository.save(user);
        return user;
    }
    public User logIn(String email, String password){
        // 1. validate if the user already exists
        Optional<User> userOptional = userRepository.findbyEmail(email);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User doesn't exist!!");
        }
        User user = userOptional.get();
        BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            return user;
        }
        throw new RuntimeException("Bad credentials");
    }
}
