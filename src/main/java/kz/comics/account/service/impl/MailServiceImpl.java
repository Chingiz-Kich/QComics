package kz.comics.account.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import kz.comics.account.model.mail.MailAttachmentDto;
import kz.comics.account.model.mail.MailDto;
import kz.comics.account.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private static final String htmlTemplatePath = "src/main/resources/mail/template.html";
    private final String htmlContent = getHtmlContent(htmlTemplatePath); // Get the HTML content from the template


    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendMail(MailDto mailDto) {
        // Creating a Mime Message
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        // Try block to check for exceptions handling
        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Setting up necessary details of mail
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(mailDto.getRecipient());
            mimeMessageHelper.setSubject(mailDto.getSubject());

            // Create a MimeMultipart object for the message content
            MimeMultipart multipart = new MimeMultipart("related");

            // Create a MimeBodyPart object for the HTML content of the message
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);

            // Loop through the image file names and add them to the message
            String[] imageFileNames = { "image-1.png", "image-2.png", "image-3.png", "image-4.png", "image-5.png", "image-6.png", "image-8.png" };
            for (String imageName : imageFileNames) {
                // Load the image from the resources folder
                InputStream imageStream = getClass().getClassLoader().getResourceAsStream("mail/images/" + imageName);
                byte[] imageBytes = IOUtils.toByteArray(imageStream);

                // Create a MimeBodyPart object for the image and set its content type
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setContent(imageBytes, "image/jpeg");


                // Add the content ID to the MimeBodyPart headers
                imagePart.setHeader("Content-ID", "<" + imageName + ">");

                // Add the image to the MimeMultipart object
                multipart.addBodyPart(imagePart);
            }

            // Set the email message content to the MimeMultipart object
            mimeMessageHelper.setText(htmlContent, true);
            mimeMessageHelper.getMimeMessage().setContent(multipart);

            // Sending the email
            mailSender.send(mimeMessage);
            return "Email has been sent successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            log.error("Error: ", e);
            return "Error while Sending email!!!";
        }
    }

    @Override
    public String sendMailWithAttachment(MailAttachmentDto mailAttachmentDto) {
        // Creating a Mime Message
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {

            // Setting multipart as true for attachment to be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(mailAttachmentDto.getRecipient());
            mimeMessageHelper.setSubject(mailAttachmentDto.getSubject());
            mimeMessageHelper.setText(mailAttachmentDto.getMsgBody());

            // Adding the file attachment
            FileSystemResource file = new FileSystemResource(new File(mailAttachmentDto.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            // Sending the email with attachment
            mailSender.send(mimeMessage);
            return "Email has been sent successfully...";
        }

        // Catch block to handle the MessagingException
        catch (MessagingException e) {
            log.error("Error: ", e);
            // Display message when exception is occurred
            return "Error while sending email!!!";
        }
    }

    @Override
    public String sendAuth(MailDto mailDto) {
        // Creating a Mime Message
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        // Try block to check for exceptions handling
        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            // Setting up necessary details of mail
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(mailDto.getRecipient());
            mimeMessageHelper.setSubject(mailDto.getSubject());

            // Sending the email
            mailSender.send(mimeMessage);
            return "Email has been sent successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            log.error("Error: ", e);
            return "Error while Sending email!!!";
        }
    }

    private static String getHtmlContent(String filePath) {
        try {
            Path path = Paths.get(filePath);
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes);
        } catch (IOException ex) {
            throw new IllegalArgumentException("getHtmlContent is wrong");
        }
    }
}
