package fun.wsss.uio.Entity.room;

import java.util.List;

public class Floor {
    private String floorName;
    private List<Room> rooms;

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}