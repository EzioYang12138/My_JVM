package cn.yangjian.monitor.client.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JinfoEntity {
    private List<String> noedefault;
    private List<String> commandLine;
}
