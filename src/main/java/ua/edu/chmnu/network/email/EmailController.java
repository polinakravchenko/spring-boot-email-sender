package ua.edu.chmnu.network.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class EmailController {

    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("/send-email")
    public String sendEmail(){
        try {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("examplemail@gmail.com");
        message.setTo("examplemail@gmail.com");
        message.setSubject("Simple text email from Polina");
        message.setText("This is a simple email body for my first email!");

        mailSender.send(message);
        return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("examplemail@gmail.com");
            //helper.setTo("examplemail0@gmail.com");

            String[] recipients = {"polliname20@gmail.com", "kravpolija@gmail.com"};
            helper.setTo(recipients);

            helper.setSubject("Java email with attachment | From Polina");
            helper.setText("Please find the attached documents below");

            helper.addAttachment("logo.jpg", new File("D:\\Users\\Полина\\Документы\\email\\src\\logo.jpg"));
            helper.addAttachment("presentation.pptx", new File("D:\\Users\\Полина\\Документы\\email\\src\\presentation.pptx"));

            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
        }

        @RequestMapping("/send-html-email")
         public String sendHtmlEmail() {
             try {
                 MimeMessage message = mailSender.createMimeMessage();
                 MimeMessageHelper helper = new MimeMessageHelper(message, true);

                 helper.setFrom("examplemail@gmail.com");
                 helper.setTo("examplemail@gmail.com");
                 helper.setSubject("Java email with attachment | From Polina");

                 try (var inputStream = Objects.requireNonNull(EmailController.class.getResourceAsStream("/templates/email-content.html"))) {
                    helper.setText(
                            new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),
                            true
                    );
                 }
                 helper.addInline("logo.jpg", new File("D:\\Users\\Полина\\Документы\\email\\src\\logo.jpg"));
                 mailSender.send(message);
                 return "success!";
             } catch (Exception e) {
                 return e.getMessage();
             }
         }
    }
