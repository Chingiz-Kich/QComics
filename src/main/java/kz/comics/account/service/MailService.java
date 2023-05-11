package kz.comics.account.service;

import kz.comics.account.model.mail.MailAttachmentDto;
import kz.comics.account.model.mail.MailDto;

public interface MailService {
    String sendMail(MailDto mailDto);
    String sendMailWithAttachment(MailAttachmentDto mailAttachmentDto);
    String sendAuth(MailDto mailDto);
}
