package fun.wsss.uio.service;

import fun.wsss.uio.model.Area;
import java.util.List;

public interface AreaService {
    List<Area> getAllAreas();
    Area getAreaById(Integer id);
} 