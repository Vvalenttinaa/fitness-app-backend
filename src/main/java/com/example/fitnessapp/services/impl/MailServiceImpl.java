package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Mail;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.LoggerService;
import com.example.fitnessapp.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
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

    private final LoggerService loggerService;

    public MailServiceImpl(UserRepository userRepository, LoggerService loggerService) {
        this.userRepository = userRepository;
        this.loggerService = loggerService;
    }

    @Override
    public void sendEmail(Mail mail, Integer userId)
    {
        loggerService.addLog("Sending email to " + mail.getMailTo());
        mail.setMailContent(generateMailContent(userId));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent(), true);
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void sendAdvisorMail(String content, String receiver) {
        loggerService.addLog("Advicer is sending email to " + receiver);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("Answer from support");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailSubscription(String mailContent, Integer userId) {
        loggerService.addLog("Sending subscription mail to user " + userId);

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
