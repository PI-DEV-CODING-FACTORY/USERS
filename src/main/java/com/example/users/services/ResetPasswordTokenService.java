package com.example.users.services;

import com.example.users.entities.PasswordResetToken;
import com.example.users.entities.User;
import com.example.users.repositories.PasswordTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResetPasswordTokenService {
    @Autowired
    private final PasswordTokenRepo passwordTokenRepo;
    @Autowired
    private JavaMailSender mailSender;
    public void saveToken(PasswordResetToken token){
        passwordTokenRepo.save(token);
        sendToken(token.getUser().getEmail(), token.getToken());
    }
    public void sendToken(String userEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Reset Password Token");
        message.setText("You have requested to reset your password. Here is your reset token:\n\n" +
                "Token: " + token + "\n\n" +
                "Please use this token to reset your password. The token is valid for 15 minutes.");
        System.out.println("Sending reset token to: " + userEmail);

        mailSender.send(message);
        System.out.println("Reset token email sent successfully!");
    }
    public User validateToken(String token){
        PasswordResetToken passwordResetToken = passwordTokenRepo.findByToken(token);
        if(passwordResetToken != null && passwordResetToken.getExpiryDate().isAfter(java.time.LocalDateTime.now())){
            return passwordResetToken.getUser();
        }
        return null;
    }
}
