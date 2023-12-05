package com.soloSavings.serviceImpl;
import com.soloSavings.service.EmailService;
import com.soloSavings.utils.ResetTokenStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
public class PasswordResetService2 {

    @Autowired
    private final ResetTokenStorage tokenStorage;
    @Autowired
    private final EmailService emailService;

    @Autowired
    public PasswordResetService2(ResetTokenStorage tokenStorage, EmailService emailService) {
        this.tokenStorage = tokenStorage;
        this.emailService = emailService;
    }

    public void initiatePasswordReset2(String userName, String userEmail, String resetToken) {
        tokenStorage.storeToken(userName, resetToken);
        emailService.sendPasswordResetEmail2(userEmail, resetToken);
    }


    public String retrieveUserName(String token) {
        String userName = tokenStorage.retrieveUsername(token);
        return userName;
    }

    public void deleteTokenStorageRecord(String userName) {
        tokenStorage.removeTokenRecord(userName);
    }

}