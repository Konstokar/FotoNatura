package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.entity.repository.UserRepository;
import com.example.demo.enums.ERole;
import com.example.demo.exceptions.UserExistException;
import com.example.demo.payload.request.SignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser (SignUpRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername()) ;
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);
        try{
            LOG. info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        }
        catch (Exception e){
            LOG.error ("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user. getUsername () + " already exist. Please check credentials");
        }
    }
}
