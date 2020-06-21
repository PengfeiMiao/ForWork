package com.mpf.forwork.controller;

//import com.mpf.forwork.customObject.ResultDTO;
import com.mpf.forwork.entity.User;
import com.mpf.forwork.messageobj.SingleMailSendObj;
import com.mpf.forwork.service.MailSendService;
//import com.mpf.forwork.service.QueueMailService;
import com.mpf.forwork.service.mq.ConsumerService;
import com.mpf.forwork.service.mq.ProducerService;
import com.mpf.forwork.staticobject.CommonStatic;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaoyin
 * @date 2018/12/13
 */
@RestController
@RequestMapping("/message")
public class MessageController {

//    @Autowired
//    private MailSendService mailSendService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ConsumerService consumerService;

    public static Boolean flag = false;

    @Resource
    User user;

    @GetMapping("/test")
    public void test() {
        System.out.println(user.getName());
/*
        SingleMailSendObj singleMailSendObj = new SingleMailSendObj();
        singleMailSendObj.setMailContent("你好啊");
        singleMailSendObj.setMailTheme("一起");
        singleMailSendObj.setAttachmentPathList(null);
        singleMailSendObj.setRecipientMail("2018224980@qq.com");
        mailSendService.inParamCallSendMail(singleMailSendObj.getRecipientMail(), singleMailSendObj.getMailTheme(), singleMailSendObj.getMailContent(), singleMailSendObj.getAttachmentPathList());
*/
/*
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        producerService.send(CommonStatic.TOPIC, "这是一条消息: "+sf.format(new Date()));

//        订阅主题和 标签（ * 代表所有标签)下信息
        if(!flag) {
            consumerService.subscribe(CommonStatic.TOPIC, "*");
            flag = true;
        }
*/
    }


    /**
     * 批量发送邮件
     *
     * @param json
     * @return
     * @author xiaoyin
     * @date 2018/12/13 21:02
     */
    @RequestMapping(value = "/message/sendMailBatch", method = RequestMethod.POST,
            consumes = "application/json")
    public void sendMailBatch(@RequestBody String json) {
//        ResultDTO<BatchMailSendObj> resultDTO = new Gson().fromJson(
//                json, new TypeToken<ResultDTO<BatchMailSendObj>>() {
//                }.getType()
//        );
//        BatchMailSendObj batchMailSendObj = resultDTO.getData();
//
//        queueMailService.inParamCallSendMail(batchMailSendObj);
    }

}
