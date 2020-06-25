package cn.yangjian.monitor.server.database.dao;

import cn.yangjian.monitor.server.database.entity.ClassLoadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassLoadRespository extends JpaRepository<ClassLoadEntity, Integer> {
    List<ClassLoadEntity> findAllByAddressAndName(String address, String name);
}
