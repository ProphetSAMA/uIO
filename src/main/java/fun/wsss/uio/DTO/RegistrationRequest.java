package fun.wsss.uio.DTO;

import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Entity.room.Room;

/**
 * 接收注册请求的实体类
 * @author Wsssfun
 */
public class RegistrationRequest {
    private User user;
    private Room room;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}