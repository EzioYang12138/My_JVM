package cn.yangjian.monitor.server.database.dao;

import cn.yangjian.monitor.server.database.entity.GcEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GcRespository extends JpaRepository<GcEntity, Integer> {
    List<GcEntity> findAllByAddressAndName(String address, String name);
}
