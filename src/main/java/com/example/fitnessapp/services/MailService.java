package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Mail;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

public interface MailService {
   /* @Async
    void sendVerificationEmail(String token,String to);
    @Async
    void sendInfoMail(String mail,String to);
*/
    @Async
    void sendAdvisorMail(String content, String receiver);

   public void sendEmail(Mail mail, Integer id);
   @Async
   void sendMailSubscription(String mail,Integer userId);
}