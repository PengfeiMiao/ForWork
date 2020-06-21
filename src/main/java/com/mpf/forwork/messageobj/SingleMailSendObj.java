package com.mpf.forwork.messageobj;

import java.util.List;

/**
 * 单个邮件发送对象
 * @author xiaoyin
 * @date 2018/12/13
 */
public class SingleMailSendObj {

   private String recipientMail;
    private String mailTheme;
    private String mailContent;
    private List<String> attachmentPathList;

    public String getRecipientMail() {
        return recipientMail;
    }

    public void setRecipientMail(String recipientMail) {
        this.recipientMail = recipientMail;
    }

    public String getMailTheme() {
        return mailTheme;
    }

    public void setMailTheme(String mailTheme) {
        this.mailTheme = mailTheme;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public List<String> getAttachmentPathList() {
        return attachmentPathList;
    }

    public void setAttachmentPathList(List<String> attachmentPathList) {
        this.attachmentPathList = attachmentPathList;
    }
}
