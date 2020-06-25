package cn.yangjian.monitor.server.remote.parm;

import cn.yangjian.monitor.server.remote.parm.entity.Address;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;


@Validated
@Component
@ConfigurationProperties(prefix="monitor")
@Data
public class AddressParm {

//    获取远程主机的地址列表
    @NotNull
    private List<Address> serve;

}
