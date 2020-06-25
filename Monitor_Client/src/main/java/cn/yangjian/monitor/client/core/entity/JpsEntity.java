package cn.yangjian.monitor.client.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class JpsEntity {
    private String className; //全名
    private String smallName; //小名
    private List<String> parameters; //参数
}
