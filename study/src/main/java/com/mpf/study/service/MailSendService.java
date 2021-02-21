package com.mpf.study.service;


import java.util.List;

public interface MailSendService {

    boolean sendMail();

    void inParamCallSendMail(String recipientMail, String mailTheme, String mailContent, List<String> attachmentPathList);

}
