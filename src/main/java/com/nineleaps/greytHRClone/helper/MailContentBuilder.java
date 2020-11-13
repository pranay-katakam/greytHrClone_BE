package com.nineleaps.greytHRClone.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import java.util.Set;


@Service
public class MailContentBuilder {
    private TemplateEngine templateEngine;
    private JavaMailSender javaMailSender;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine,JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender=javaMailSender;
    }


    public String generateMailContent() {
        Context context = new Context();

        return templateEngine.process("emailtemp", context);
    }

    @Async
    public void sendWelcomeMail() {
        String text = generateMailContent();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.addInline("nl_logo", new ClassPathResource("images/nl_logo.png"),"image/png");


            helper.setFrom("greythrclone1@gmail.com");
            helper.setTo("hithesh.kumar@nineleaps.com");
            helper.setSubject("This is a test mail from greythr clone");
            helper.setText(text, true);

            javaMailSender.send(message);


        } catch (MessagingException e) {
            System.out.println("something went wrong in mail template");
        }
    }



}
