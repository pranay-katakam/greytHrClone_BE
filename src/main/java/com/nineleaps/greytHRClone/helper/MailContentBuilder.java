package com.nineleaps.greytHRClone.helper;


import com.nineleaps.greytHRClone.dto.ProfileDTO;
import com.nineleaps.greytHRClone.dto.TotalTimeDTO;
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
        Map<String, String> inlineImages = new HashMap<>();
        inlineImages.put("facebookIcon", "images/facebook.png");
        inlineImages.put("instagramIcon", "images/instagram.png");
        inlineImages.put("linkedinIcon", "images/linkedin.png");
        inlineImages.put("nl_logo", "images/nl_logo.png");
        inlineImages.put("OneLogo", "images/OneLogo.png");
        inlineImages.put("topStartup", "images/topStartup.jpeg");
        inlineImages.put("twitterIcon", "images/twitter.png");
        inlineImages.put("welcome", "images/welcome.jpg");

        String text = generateMailContent(profileDTO);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);





            helper.setFrom("greythrclone1@gmail.com");
            helper.setTo("mohammad.shinaz@nineleaps.com");
            helper.setSubject("This is a test mail from greythr clone" );
            helper.setText(text, true);

            Set<String> setImageID = inlineImages.keySet();
            for (String contentId : setImageID) {
                helper.addInline(contentId, new ClassPathResource(inlineImages.get(contentId)));
            }

            javaMailSender.send(message);


        } catch (MessagingException e) {
            System.out.println("something went wrong in mail template");
        }
    }


    public void sendDeductionMail(TotalTimeDTO totalTimeDTO){

    }



}
