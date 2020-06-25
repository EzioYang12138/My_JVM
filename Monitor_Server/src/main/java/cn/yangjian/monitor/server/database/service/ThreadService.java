package cn.yangjian.monitor.server.database.service;

import cn.yangjian.monitor.server.core.JstackEntity;
import cn.yangjian.monitor.server.database.dao.ThreadRespository;
import cn.yangjian.monitor.server.database.entity.ThreadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {
    @Autowired
    private ThreadRespository threadRespository;

    public List<ThreadEntity> findAllByAddressAndName(String address, String name) {
        return threadRespository.findAllByAddressAndName(address, name);
    }

    public void write(String address, String name, String date, JstackEntity jstatk) {
        ThreadEntity entity = new ThreadEntity();
        entity.setAddress(address);
        entity.setName(name);
        entity.setDate(date);
        entity.setTotal(jstatk.getTotal());
        entity.setRUNNABLE(jstatk.getRUNNABLE());
        entity.setTIMED_WAITING(jstatk.getTIMED_WAITING());
        entity.setWAITING(jstatk.getWAITING());
        threadRespository.save(entity);
    }

    public void clearAll() {
        threadRespository.deleteAll();
    }
}
