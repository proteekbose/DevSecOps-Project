package com.soloSavings.controller;


import com.soloSavings.service.EmailService;
import com.soloSavings.serviceImpl.SecurityContextImpl;
import com.soloSavings.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    SecurityContextImpl securityContext;
    @Autowired
    EmailService emailService; // Assuming you have an EmailService bean


    @RequestMapping(value = "balance", method = RequestMethod.GET)
    public ResponseEntity<?> getTotalBalance (){
        securityContext.setContext(SecurityContextHolder.getContext());
        Double balance = userServiceImpl.getBalance(securityContext.getCurrentUser().getUser_id());
        securityContext.dispose();
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }


    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String username) {
        String email = userServiceImpl.getEmailByUsername(username);

        if (email != null) {
            String otp = generateRandomOTP(); // Implement this method to generate OTP
            emailService.sendOtpEmail(email, otp);

            // Show a message indicating that OTP has been sent
            return ResponseEntity.ok("OTP has been sent to your email.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
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


    @GetMapping("/get-email")
    public ResponseEntity<String> getEmailByUsername(@RequestParam String username) {

        System.out.println("hi");

        System.out.println("Reached getEmailByUsername method with username: " + username);

        String email = userServiceImpl.getEmailByUsername(username);

        if (email != null) {
            return ResponseEntity.ok(email);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found for the given username.");
        }
    }
}
