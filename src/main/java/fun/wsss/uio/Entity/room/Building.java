package fun.wsss.uio.Entity.room;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 楼号实体类
 * @author Wsssfun
 */
@TableName("buildings")
public class Building {
    @TableId
    private Long buildingId;
    private String buildingName;

    // Getters and Setters

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}