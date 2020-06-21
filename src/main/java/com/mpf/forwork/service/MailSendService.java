package com.mpf.forwork.service;


import java.util.List;

public interface MailSendService {

    boolean sendMail();

    void inParamCallSendMail(String recipientMail, String mailTheme, String mailContent, List<String> attachmentPathList);

}
