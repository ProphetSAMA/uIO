package fun.wsss.uio.model.room;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 楼栋实体类
 * @author Wsssfun
 */
@Setter
@Getter
@Data
@TableName("building")
public class Building {
    @TableId
    private Integer id;
    private String name;

}

