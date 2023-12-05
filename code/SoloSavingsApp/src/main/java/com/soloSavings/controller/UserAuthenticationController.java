package com.soloSavings.controller;

import com.soloSavings.config.JwtUtil;
import com.soloSavings.model.Login;
import com.soloSavings.model.ResetPassword;
import com.soloSavings.model.TokenDetails;
import com.soloSavings.model.User;
import com.soloSavings.service.UserService;
import com.soloSavings.serviceImpl.PasswordResetService;
import com.soloSavings.serviceImpl.PasswordResetService2;
import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

import static com.soloSavings.utils.Constants.INVALID_USERNAME_OR_PASSWORD;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@RestController
@RequestMapping("/api")
public class UserAuthenticationController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserAuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

    private static String otp="";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordResetService2 passwordResetService2;
    /*@Autowired
    AuthenticationManager authenticationManager;*/
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody User user) {
        logger.info("Request to create a new user: {}", user);
        try {
            userService.save(user);
            return ResponseEntity.ok().body("The user account with email has successfully created");
        } catch (NonUniqueResultException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>("The email has already registered.", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody Login loginData) {
        logger.info("Request to log in as the user: {}", loginData.username());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.username(), loginData.password()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_USERNAME_OR_PASSWORD);
        }

        UserDetails userDetails = userService.loadUserByUsername(loginData.username());
        TokenDetails token = jwtUtil.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @RequestMapping(value = "/forget-password", method = RequestMethod.POST)
    public ResponseEntity forgetPassword(@RequestBody Login loginData) {
        System.out.println("\n\n\n\n\n\nPRO - HI1\n\n\n\n\n\n\n");
        try {
            UserDetails userDetails = userService.loadUserByUsername(loginData.username());
            try {
                User userInfo = userService.getUserByName(userDetails.getUsername());
                logger.info("Request to send forget password link as the user: {}", loginData.username());
                passwordResetService.initiatePasswordReset(userInfo.getUsername(), userInfo.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Issue with sending email.");
            }
            return ResponseEntity.ok("User found, email sent");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }


    @RequestMapping(value = "/forget-password2", method = RequestMethod.POST)
    public ResponseEntity forgetPassword2(@RequestBody Login loginData) {
        System.out.println("\n\n\n\n\n\nPRO - HI2\n\n\n\n\n\n\n");
        try {
            UserDetails userDetails = userService.loadUserByUsername(loginData.username());
            try {
                String  resetToken = generateRandomOTP();
                otp = resetToken;
                User userInfo = userService.getUserByName(userDetails.getUsername());
                logger.info("Request to send forget password link as the user: {}", loginData.username());
                passwordResetService2.initiatePasswordReset2(userInfo.getUsername(), userInfo.getEmail(), resetToken);
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Issue with sending email.");
            }
            return ResponseEntity.ok("User found, email sent");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    private String generateRandomOTP() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 6; // You can adjust the length of the OTP as needed
        StringBuilder otp = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(characters.length());
            otp.append(characters.charAt(index));
        }

        return otp.toString();
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ResponseEntity resetPassword(@RequestBody ResetPassword resetPasswordData) {

        try {
            String userName = passwordResetService.retrieveUserName(resetPasswordData.token());

            if (userName.equals(resetPasswordData.username())) {

                userService.setUserNewPassword(userName, resetPasswordData.password());
                passwordResetService.deleteTokenStorageRecord(userName);
                return ResponseEntity.ok("Password reset successfully");
            } else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong.");
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }


    @RequestMapping(value = "/reset-password2", method = RequestMethod.POST)
    public ResponseEntity resetPassword2(@RequestBody ResetPassword resetPasswordData, HttpServletRequest request) {
        System.out.println("\n\n\n\n\n\nPRO");
        try {
            String userName = passwordResetService2.retrieveUserName(resetPasswordData.token());
            System.out.println("HI4- "+resetPasswordData.token());
            System.out.println("Hi4- "+otp+"\n\n\n\n\n");
            if (otp.equals(resetPasswordData.token())) {

                System.out.println("Hi8- Matched "+resetPasswordData.username()+"  "+resetPasswordData.password());
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                        resetPasswordData.username(), resetPasswordData.password());
                Authentication authentication = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Generate a new JWT token
                TokenDetails token = jwtUtil.generateToken(resetPasswordData.username());

                // Set the user details in the session
                request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                System.out.println("HI1");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong.");
            }
        } catch (Exception e) {
            System.out.println("HI2");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }
}
