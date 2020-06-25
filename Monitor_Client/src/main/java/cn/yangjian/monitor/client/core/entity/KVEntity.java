package cn.yangjian.monitor.client.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class KVEntity {
    private String key;
    private String value;
}
