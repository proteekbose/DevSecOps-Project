package com.soloSavings.service;

public interface EmailService {
    public void sendPasswordResetEmail(String toEmail, String resetToken);

    public void sendPasswordResetEmail2(String toEmail, String resetToken);

    public void sendOtpEmail(String toEmail, String otp);
}

