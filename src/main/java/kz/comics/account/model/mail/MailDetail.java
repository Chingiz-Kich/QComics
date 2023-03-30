package kz.comics.account.model.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class MailDetail {

    /** Recipient email address **/
    private String recipient;

    /** Email subject **/
    private String subject;

    /** Message body **/
    private String msgBody;

    /** Attachment (only if needed) **/
    private String attachment;
}
