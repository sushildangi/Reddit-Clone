package com.luv2tech.service;

import com.luv2tech.exceptions.SpringRedditException;
import com.luv2tech.response.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Override
    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("springreddit@gmail.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));

        };
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("Activation email sent!!!");
        } catch (MailException e) {
            log.error(e.getLocalizedMessage());
            throw new SpringRedditException("Exception occurred when sending mail to -" +
                    notificationEmail.getRecipient());

        }
    }
}
