/**
 *
 */
package com.marcos.moiploja.common.services;

import com.marcos.moiploja.MoiplojaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author Marcos
 */
@Component
public class EmailService {
    private static final MLLogger logger = MLLogger.getLogger(EmailService.class);

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${support.email}")
    String supportEmail;

    public void sendEmail(String to, String subject, String content) throws MoiplojaException {
        try {
            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom(supportEmail);
            message.setTo(to);
            message.setText(content, true /* isHtml */);

            javaMailSender.send(message.getMimeMessage());
        } catch (MailException | MessagingException e) {
            logger.error(e);
            throw new MoiplojaException("Unable to send email");
        }
    }


}
