package fun.wsss.uio.Entity.room;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("rooms")
public class Room {
    @TableId
    private Long roomsId;   //id
    private String  roomName;   //名称

    private Long parentId;    //父级id

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    private List <Room> rooms;

    public Long getRoomsId() {
        return roomsId;
    }

    public void setRoomsId(Long roomsId) {
        this.roomsId = roomsId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}