package fun.wsss.uio.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体类
 * @author Wsssfun
 */
@Getter
@Setter
@Data
@TableName("user")
public class User {
    private Long id;
    private String username;
    private String password;
    private Integer buildingId;
    private Integer floorId;
    private Integer roomId;
}