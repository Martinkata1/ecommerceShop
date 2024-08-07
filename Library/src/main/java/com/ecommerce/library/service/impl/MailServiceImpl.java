/* package com.ecommerce.library.service.impl;

import com.ecommerce.library.service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;
    @Override
    public void sendMailTest() {
        // TODO: Implementation to send email using SMTP or any other suitable method
        try{
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setFrom(email);
            helper.setTo("balkansofra@gmail.com");
            helper.setSubject("Test mode");
            helper.setText("Здравейте, как мога да ви бъда полезен?");

            mailSender.send(message);

        }catch(Exception e){
            e.printStackTrace();
            //System.out.println("Error sending email: " + e.getMessage());
        }
    }


}
*/