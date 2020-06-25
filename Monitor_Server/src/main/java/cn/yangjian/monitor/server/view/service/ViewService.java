package cn.yangjian.monitor.server.view.service;

import cn.yangjian.monitor.server.remote.CallingMethod;
import cn.yangjian.monitor.server.remote.parm.entity.Address;
import com.alibaba.fastjson.JSON;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@CacheConfig(cacheNames = {"myCache"})
public class ViewService {

    @Cacheable(key ="targetClass + methodName +#p0")
    public Map<String,Object> getIndex(Address address) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("address",address);
        map.put("jps", CallingMethod.getJps(address.getAddress()));
        return map;
    }

    @Cacheable(key ="targetClass + methodName +#p0")
    public Map<String,Object> getMain(Address address) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("jps",CallingMethod.getJps(address.getAddress()));
        map.put("system", JSON.parseObject(CallingMethod.getSystem(address.getAddress()),Map.class));
        map.put("version",CallingMethod.getVersion(address.getAddress()));
        map.put("address",address);
        return map;
    }
}
