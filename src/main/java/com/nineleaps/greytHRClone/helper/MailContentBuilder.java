package com.nineleaps.greytHRClone.helper;


import com.nineleaps.greytHRClone.dto.ProfileDTO;
import com.nineleaps.greytHRClone.dto.TotalTimeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;


@Service
public class MailContentBuilder {
    private TemplateEngine templateEngine;
    private JavaMailSender javaMailSender;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine,JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender=javaMailSender;
    }


    public String generateMailContent(ProfileDTO profileDTO) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", profileDTO.getName());
        model.put("userImage",profileDTO.getImageName());
        Context context = new Context();
        context.setVariables(model);

        return templateEngine.process("emailtemp", context);
    }

    @Async
    public void sendWelcomeMail(ProfileDTO profileDTO) {

        String text = generateMailContent(profileDTO);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setFrom("greythrclone1@gmail.com");
            helper.setTo("mohammad.shinaz@nineleaps.com");
            helper.setSubject("This is a test mail from greythr final clone" );
            helper.setText(text, true);

            javaMailSender.send(message);


        } catch (MessagingException e) {
            System.out.println("something went wrong in mail template");
        }
    }


    public void sendDeductionMail(TotalTimeDTO totalTimeDTO){

    }



}
