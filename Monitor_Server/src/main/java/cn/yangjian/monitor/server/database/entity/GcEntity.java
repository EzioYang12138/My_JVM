package cn.yangjian.monitor.server.database.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


//jstat -gc
//监视Java堆状况，包括Eden区、两个Survivor区、老年代等容量、已用空间、GC时间合计等信息

@Data
@Entity
@Table(name = "gc_table")
public class GcEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String address; //进程所在主机
    private String name; //进程ID
    private String date; //x：时间
    private String S0C;//第一个幸存区的大小
    private String S1C;//第二个幸存区的大小
    private String S0U;//第一个幸存区的使用大小
    private String S1U;//第二个幸存区的使用大小
    private String EC;//伊甸园区的大小
    private String EU;//伊甸园区的使用大小
    private String OC;//老年代大小
    private String OU;//老年代使用大小
    private String MC;//方法区大小
    private String MU;//方法区使用大小
    private String CCSC;//压缩类空间大小
    private String CCSU;//压缩类空间使用大小
    private String YGC;//年轻代垃圾回收次数
    private String YGCT;//年轻代垃圾回收消耗时间
    private String FGC;//老年代垃圾回收次数
    private String FGCT;//老年代垃圾回收消耗时间
    private String GCT;//垃圾回收消耗总时间

}
