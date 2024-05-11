package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Mail;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MailServiceImpl implements MailService
{
    @Autowired
    private JavaMailSender javaMailSender;
    final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String from;

    public MailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void sendEmail(Mail mail, Integer userId)
    {
        mail.setMailContent(generateMailContent(userId));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent(), true); // Postavite true kao drugi argument za označavanje HTML sadržaja
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailSubscription(String mailContent, Integer userId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("Obavjestenje o novim programima");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(userEntity.get().getMail());
            mimeMessageHelper.setText(mailContent, true);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public String generateHTMLLink(String url, String linkText) {
        return "<a href=\"" + url + "\">" + linkText + "</a>";
    }

    public String generateMailContent(Integer userId) {
        String link = generateHTMLLink("http://localhost:8080/api/auth/activate/" + userId, "AKTIVACIJA NALOGA");
        return "Postovani, <br><br> Vas zahtjev za registraciju je primljen,<br> klikom ga aktivirajte. " + link + " <br><br> S poštovanjem, <br> Vasa FitnessApp";
    }
}

/*
    @Value("${spring.mail.username}")
    private String fromMail;

    @Value("${account.verification.url}")
    private String accountVerificationUrl;

    private final HttpServletRequest request;

    @Async
    public void sendVerificationEmail(String token, String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setSubject("Account verification, Pocket Wedding");
            ClassPathResource htmlPath = new ClassPathResource("AccountVerification.html");
            var html = Files.readString(Path.of(htmlPath.getFile().getAbsolutePath()));
            html = html.replace("validation.url", accountVerificationUrl + token);
            helper.setText(html, true);
            helper.setFrom(fromMail);
            helper.setTo(to);
            System.out.println(fromMail);
            this.mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendInfoMail(String mail, String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromMail);
            message.setTo(to);
            message.setSubject("Daily Fitness programs");
            message.setText(mail);
            this.mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void sendAdvisorMail(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromMail);
            helper.setTo(email.getTo());
            if (email.getSubject() != null) {
                helper.setSubject(email.getSubject());
            }
            if (email.getAttachmentId() != null) {
                var path = this.imageService.getPathById(email.getAttachmentId());
                FileSystemResource file = new FileSystemResource(new File(path[0]));
                helper.addAttachment(path[1], file);
            }

            helper.setText(email.getMessage());
            this.mailSender.send(message);
            if (email.getAttachmentId() != null)
                this.imageService.deleteImageById(email.getAttachmentId());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

 */
//}