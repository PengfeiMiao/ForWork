package com.mpf.forwork.service.impl;

import com.mpf.forwork.service.MailSendService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

@Service
public class MailSendServiceImpl implements MailSendService {

    String to = "";// 收件人
    String from = "";// 发件人
    String host = "";// smtp主机
    String username = "";
    String password = "";
    String filename = "";// 附件文件名
    String subject = "";// 邮件主题
    String content = "";// 邮件正文
    Vector file = new Vector();// 附件文件集合

    private static String testString = "2018224980@qq.com"; //许

    /**
     * 输入参数，调用发送邮件方法
     *
     * @param recipientMail
     * @param mailTheme
     * @param mailContent
     * @param attachmentPathList
     */
    @Override
    public void inParamCallSendMail(String recipientMail, String mailTheme, String mailContent, List<String> attachmentPathList) {
        MailSendServiceImpl sendmail = new MailSendServiceImpl();

        sendmail.setHost("mail.cqu.edu.cn");// smtp.mail.yahoo.com.cn/smtp.163.com
        sendmail.setUserName("cdjx@cqu.edu.cn");// 您的邮箱用户名
        sendmail.setPassWord("jwcmis632&&^");// 您的邮箱授权码
        sendmail.setTo(recipientMail);// 接收者1285878093

        sendmail.setFrom("cdjx@cqu.edu.cn");// 发送者
        sendmail.setSubject(mailTheme);//邮件主题
        sendmail.setContent(mailContent);//邮件内容

        if (attachmentPathList == null) {
            attachmentPathList = new ArrayList<>();
        }
        for (String per : attachmentPathList) {
            sendmail.attachfile(per);//邮件附件
        }
        try {
            String url = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServerName();
            if (!url.contains("my")) {
                sendmail.setTo(testString);
            }
        } catch (Exception e) {
        }
        sendmail.sendMail();//调用具体发送方法
    }


    /**
     * 发送邮件
     *
     * @return
     */
    @Override
    public boolean sendMail() {

        // 构造mail session
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // 构造MimeMessage 并设定基本的值
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            // TODO: 2017/3/16 单发邮件 该处已注释
            InternetAddress[] address = {new InternetAddress(to)};
            // TODO: 2017/3/16 群发邮件
//            InternetAddress[] address = null;
            if (to != null) {
                String[] arr = to.split(",");
                if (arr != null && arr.length != 0) {
                    InternetAddress[] newAddress = new InternetAddress[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        newAddress[i] = new InternetAddress(arr[i]);
                    }
                    address = newAddress;
                }
            }

            msg.setRecipients(Message.RecipientType.TO, address);
            //subject = transferChinese(subject);
            msg.setSubject(subject);
            // 构造Multipart
            Multipart mp = new MimeMultipart();
            // 向Multipart添加正文
            BodyPart mbpContent = new MimeBodyPart();
            mbpContent.setContent(content, "text/html;charset=gb2312");
            // 向MimeMessage添加（Multipart代表正文）
            mp.addBodyPart(mbpContent);
            // 向Multipart添加附件
            Enumeration efile = file.elements();
            while (efile.hasMoreElements()) {
                MimeBodyPart mbpFile = new MimeBodyPart();
                filename = efile.nextElement().toString();
                FileDataSource fds = new FileDataSource(filename);
                mbpFile.setDataHandler(new DataHandler(fds));
                // 解决附件名称乱码
                mbpFile.setFileName(MimeUtility.encodeText(fds.getName(),"utf-8",null));
                // 向MimeMessage添加（Multipart代表附件）
                mp.addBodyPart(mbpFile);
            }
            file.removeAllElements();
            // 向Multipart添加MimeMessage
            msg.setContent(mp);
            msg.setSentDate(new Date());
            // 发送邮件
            Transport.send(msg);
            System.out.println("发送成功！");
        } catch ( Exception mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    }





    /**
     * 设置邮件服务器地址
     *
     * @param host 邮件服务器地址名称
     */
    public void setHost(String host) {
        this.host = host;
    }


    /**
     * 设置登录服务器校验密码
     *
     * @param pwd
     */
    public void setPassWord(String pwd) {
        this.password = pwd;
    }


    /**
     * 设置登录服务器校验用户
     *
     * @param usn
     */
    public void setUserName(String usn) {
        this.username = usn;
    }


    /**
     * 设置邮件发送目的邮箱
     *
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }


    /**
     * 设置邮件发送源邮箱
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }


    /**
     * 设置邮件主题
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }


    /**
     * 设置邮件内容
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * 把主题转换为中文
     *
     * @param strText
     * @return
     */
    public String transferChinese(String strText) {
        try {
            // subject =
            //strText = MimeUtility.encodeText(new String(strText.getBytes(), "GB2312"), "GB2312", "B");
            strText = new String(strText.getBytes("ISO-8859-1"), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strText;
    }


    /**
     * 往附件组合中添加附件
     *
     * @param fname
     */
    public void attachfile(String fname) {
        file.addElement(fname);
    }


}
