package cn.yangjian.monitor.server.database.dao;

import cn.yangjian.monitor.server.database.entity.ThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRespository extends JpaRepository<ThreadEntity, Integer> {
    List<ThreadEntity> findAllByAddressAndName(String address, String name);
}
