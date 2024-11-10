package org.mainfest.devSquare.DevSqaure.controller;

import jakarta.validation.Valid;
import org.mainfest.devSquare.DevSqaure.entities.USER;
import org.mainfest.devSquare.DevSqaure.entities.UserDto;
import org.mainfest.devSquare.DevSqaure.services.UserService;
import org.mainfest.devSquare.DevSqaure.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRegisterationController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/sign_up")
    public ResponseEntity<?> createUser(@RequestBody @Valid USER user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
        }
        USER save = userService.save(user);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> createToken(@RequestBody UserDto user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(Map.of("token",jwt),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/validate-token/{token}")
    public ResponseEntity<Map<String,String>> validate(@PathVariable String token){
        if(!jwtUtils.isExpired(token)){
            return ResponseEntity.ok(Map.of("status","valid"));
        }
        return ResponseEntity.ok(Map.of("status","invalid"));
    }


    @GetMapping("/invalidate")
    public ResponseEntity<?> invalidate(){
        return ResponseEntity.ok(Map.of("status",""+userService.invalidate()));
    }

}
