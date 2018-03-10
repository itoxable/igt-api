package uk.doneby.igt.service;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Component
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Value("${dont.reply.email.address}")
	private String dontReplyEmailAddress;
	
	@Value("${amin.email.address}")
	private String aminEmailAddress;
	
	@Autowired
	Configuration freemakerconfiguration;

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
	
	public void sendEmail(String to, String subject, String templateName, Map<String, String> data, boolean adminEmail) throws MessagingException,
			TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper;
		
		String fromAddress = dontReplyEmailAddress;
		
		if(adminEmail){
			fromAddress = aminEmailAddress;
		}
		
		helper = new MimeMessageHelper(message, true);
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setFrom(fromAddress);
		helper.setText(getEmailContent(templateName, data));
		
		javaMailSender.send(message);

	}

	public void sendEmail(String fromAddress, String[] to, String subject, String text, boolean asHtml) throws MessagingException,
		TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
	
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
				
		helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setFrom(fromAddress);
		helper.setText(text, asHtml);
		
		javaMailSender.send(message);
	
	}
	
	public String getEmailContent(String templateName, Map<String, String> data) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreeMarkerTemplateUtils.processTemplateIntoString(freemakerconfiguration.getTemplate(templateName), data);
	}

}
