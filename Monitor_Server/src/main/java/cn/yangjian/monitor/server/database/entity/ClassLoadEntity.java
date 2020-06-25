package cn.yangjian.monitor.server.database.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


//jstat -class

@Data
@Entity
@Table(name = "class_table")
public class ClassLoadEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String address; //进程所在主机
    private String name; //进程ID
    private String date; //x：时间
    private String Loaded;//加载class的数量
    private String Bytes1;//占用空间的大小
    private String Unloaded;//未加载的class的数量
    private String Bytes2;//未加载占用的空间
    private String Time1;
    private String Compiled;
    private String Failed;
    private String Invalid;
    private String Time2;

}
