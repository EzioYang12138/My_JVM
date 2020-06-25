package cn.yangjian.monitor.client.order;

import cn.yangjian.monitor.client.core.entity.JinfoEntity;
import cn.yangjian.monitor.client.core.entity.JpsEntity;
import cn.yangjian.monitor.client.core.entity.JstackEntity;
import cn.yangjian.monitor.client.core.entity.KVEntity;
import cn.yangjian.monitor.client.core.order.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @RequestMapping("/version")
    public String getVersion(){
        return Javav.version();
    }

    @RequestMapping("/info")
    public JinfoEntity getInfo(String id){
        return Jinfo.info(id);
    }

    @RequestMapping("/jstack")
    public JstackEntity getJstack(String id){
        return Jstack.jstack(id);
    }

    @RequestMapping("/jps")
    public Map<String, JpsEntity> getJps(){
        return Jps.jps();
    }

    @RequestMapping("/jstatclass")
    public List<KVEntity> getJstatClass(String id) throws Exception {
        return Jstat.jstatClass(id);
    }

    @RequestMapping("/jstatgc")
    public List<KVEntity> getJstatGc(String id) throws Exception {
        return Jstat.jstatGc(id);
    }

    @RequestMapping("/jstatutil")
    public List<KVEntity> getJstatUtil(String id) throws Exception {
        return Jstat.jstatUtil(id);
    }

}
