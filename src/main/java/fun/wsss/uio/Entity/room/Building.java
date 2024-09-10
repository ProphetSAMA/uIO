package fun.wsss.uio.Entity.room;

import java.util.List;

public class Building {
    private String buildingName;
    private List<Floor> floors;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }
}