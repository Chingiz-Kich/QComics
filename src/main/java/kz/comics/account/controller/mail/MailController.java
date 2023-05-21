package kz.comics.account.controller.mail;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.mail.MailAttachmentDto;
import kz.comics.account.model.mail.MailDto;
import kz.comics.account.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @Operation(summary = "Sending email does not require attachment in request body")
    @PostMapping("/send")
    public String sendMail(@RequestBody MailDto mailDto) {
        return mailService.sendMail(mailDto);
    }

    @Operation(summary = "Sending email with attachment")
    @PostMapping("/send-attachment")
    public String sendMailWithAttachment(@RequestBody MailAttachmentDto mailAttachmentDto) {
        return mailService.sendMailWithAttachment(mailAttachmentDto);
    }
}
