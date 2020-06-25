package cn.yangjian.monitor.server.socket.controller;

import cn.yangjian.monitor.server.database.entity.ClassLoadEntity;
import cn.yangjian.monitor.server.database.entity.GcEntity;
import cn.yangjian.monitor.server.database.entity.ThreadEntity;
import cn.yangjian.monitor.server.database.service.ClassService;
import cn.yangjian.monitor.server.database.service.GcService;
import cn.yangjian.monitor.server.database.service.ThreadService;
import cn.yangjian.monitor.server.view.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GreetingController {

    @Autowired
    private GcService gcService;
    @Autowired
    private ClassService classService;
    @Autowired
    private ThreadService threadService;

    @MessageMapping("/gc")  //全局配置中指定了服务端接收的连接以 app开头，所以客户端发送公告的请求连接应该是/app/gc
    @SendTo("/topic/gc")    //订阅服务端消息时指定的一个地址，用于接收服务端的返回
    public List<GcEntity> socketGc(Message message) throws Exception {
        return gcService.findAllByAddressAndName(message.getAddress(), message.getPid());
    }

    @MessageMapping("/class")
    @SendTo("/topic/class")
    public List<ClassLoadEntity> socketClass(Message message) throws Exception {
        return classService.findAllByAddressAndName(message.getAddress(), message.getPid());
    }

    @MessageMapping("/thread")
    @SendTo("/topic/thread")
    public List<ThreadEntity> socketThread(Message message) throws Exception {
        return threadService.findAllByAddressAndName(message.getAddress(), message.getPid());
    }

}
