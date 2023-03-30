package kz.comics.account.model.mail;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailAttachmentDto extends MailDetail{

    /** Attachment **/
    private String attachment;
}
