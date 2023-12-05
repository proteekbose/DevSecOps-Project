package com.soloSavings.serviceImpl;

import com.soloSavings.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("notification.solosavings@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Reset Password with Solo-Savings");
        mailMessage.setText("To reset your password, please copy paste the reset token to the reset password form:\n"
                + resetToken);

        javaMailSender.send(mailMessage);
    }

    public void sendPasswordResetEmail2(String toEmail, String resetToken) {


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("notification.solosavings@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("SoloSavings - OTP Verification Code");
        mailMessage.setText("Dear User,\n\nThank you for choosing SoloSavings!\n\nTo enhance the security of your account, we've initiated an OTP (One-Time Password) verification process. Please use the following code to complete your login:\n\nVerification Code: "
                + resetToken + "\n\nSimply enter this code in the OTP Verification form to access your account securely.\n\nIf you did not request this OTP or encounter any issues, please contact our support team immediately.\n\n\nBest Regards, \nSoloSavings Team");

        javaMailSender.send(mailMessage);
    }



    @Override
    public void sendOtpEmail(String toEmail, String otp) {
        // Implement the logic to send an email with OTP
        // Use your mail server details and JavaMailSender or any other library

        // Example: Assuming you have a JavaMailSender bean configured
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("OTP for Login");
        message.setText("Your OTP is: " + otp);

        javaMailSender.send(message);
    }
}
