package com.mpf.forwork.controller;

//import com.mpf.forwork.customObject.ResultDTO;
import com.mpf.forwork.entity.User;
import com.mpf.forwork.messageobj.Message;
import com.mpf.forwork.messageobj.SingleMailSendObj;
import com.mpf.forwork.netty.client.NettyClient;
import com.mpf.forwork.netty.protocol.protobuf.MessageBase;
import com.mpf.forwork.service.MailSendService;
//import com.mpf.forwork.service.QueueMailService;
import com.mpf.forwork.service.kafka.KafkaProducer;
import com.mpf.forwork.service.mq.ConsumerService;
import com.mpf.forwork.service.mq.ProducerService;
import com.mpf.forwork.staticobject.CommonStatic;
import com.mpf.forwork.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.mpf.forwork.staticobject.CommonStatic.threadLocal;

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
}
