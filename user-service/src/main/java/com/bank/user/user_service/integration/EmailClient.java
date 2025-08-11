package com.bank.user.user_service.integration;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailClient {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    public void sendSuccessRegistrationEmail(String toEmail, String userName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String subject = "🎉 Welcome to DigitalWallet, " + userName + "! Registration Successful";

        String text = String.format(
                "<html>"
                        + "<body style='font-family: Arial, sans-serif;'>"
                        + "<h2 style='color:#2962ff'>Welcome to <b>DigitalWallet</b>, %s!</h2>"
                        + "<p>Dear %s,</p>"
                        + "<p>Congratulations! Your registration with <b>DigitalWallet</b> was successful. We're excited to have you on board.</p>"
                        + "<hr>"
                        + "<p style='font-size:13px; color:#888;'>If you have any questions or need assistance, feel free to reply to this email or contact our support team.</p>"
                        + "<p style='font-size:14px;'>Best regards,<br><b>DigitalWallet</b> Team</p>"
                        + "</body>"
                        + "</html>",
                userName, userName);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(text, true); // Set to true to enable HTML content

        mailSender.send(message);
    }


}
