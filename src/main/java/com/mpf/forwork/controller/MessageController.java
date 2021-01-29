package com.mpf.forwork.controller;

import com.mpf.forwork.annotation.SysLogs;
import com.mpf.forwork.annotation.SysMsg;
import com.mpf.forwork.messageobj.Message;
import com.mpf.forwork.netty.client.NettyClient;
import com.mpf.forwork.netty.protocol.protobuf.MessageBase;
import com.mpf.forwork.service.TestService;
import com.mpf.forwork.service.kafka.KafkaProducer;
import com.mpf.forwork.service.mq.ConsumerService;
import com.mpf.forwork.service.mq.ProducerService;
import com.mpf.forwork.staticobject.CommonStatic;
import com.mpf.forwork.util.RedisUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


/**
 * @author mpf
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

//    @Autowired
//    private ScheduledTask scheduledTask;

    @Autowired
    private RedisUtil redisUtil;

    public static Boolean flag = false;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) throws InterruptedException {
/*
        SingleMailSendObj singleMailSendObj = new SingleMailSendObj();
        singleMailSendObj.setMailContent("你好啊");
        singleMailSendObj.setMailTheme("一起");
        singleMailSendObj.setAttachmentPathList(null);
        singleMailSendObj.setRecipientMail("2018224980@qq.com");
        mailSendService.inParamCallSendMail(singleMailSendObj.getRecipientMail(), singleMailSendObj.getMailTheme(), singleMailSendObj.getMailContent(), singleMailSendObj.getAttachmentPathList());
*/
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        producerService.send(CommonStatic.TOPIC, sf.format(new Date())+"=>消息内容:"+message);

//        订阅主题和 标签（ * 代表所有标签)下信息
        if(!flag) {
            consumerService.subscribe(CommonStatic.TOPIC, "*");
            flag = true;
        }
        redisUtil.set("mq", null);
        while(redisUtil.get("mq")==null) {
            Thread.sleep(100);
        }
        return redisUtil.get("mq").toString();
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

    }

    @Autowired
    private KafkaProducer producer;

    @GetMapping("/kafka/send")
    public String sendKafka() {
        producer.send(new Message("test"));
        return "kafka send ok";
    }

    @GetMapping("/kafka/task")
    public String sendTask() {
        producer.sendTask();
//        new ScheduledTask(10);
        return "kafka send task";
    }

    @Autowired
    private NettyClient nettyClient;

    @GetMapping("/netty/send")
    public String sendNetty(@RequestParam String msg) {
        MessageBase.Message message = new MessageBase.Message()
                .toBuilder().setCmd(MessageBase.Message.CommandType.NORMAL)
                .setContent(msg)
                .setRequestId(UUID.randomUUID().toString()).build();
        nettyClient.sendMsg(message);
        return "netty send ok";
    }

    @Autowired
    TestService testService;

    /**
     * 此接口用来测试指定方法的aop拦截以及消息推送
     * @param id
     * @param msg
     * @return
     */
    @SysLogs
    @GetMapping("/test")
    public String test(@RequestParam String id, @RequestParam String msg) {
        return testService.test(id+"@"+msg);
    }

    @Data
    class MyObject{
        String test;
    }

    @SysMsg
    @PostMapping("/test2")
    public String test2(@RequestParam String id, @RequestParam String msg, @RequestBody MyObject body) {
        return id+"@"+msg;
    }
}
