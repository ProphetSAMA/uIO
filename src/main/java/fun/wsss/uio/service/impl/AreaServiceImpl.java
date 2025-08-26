package fun.wsss.uio.service.impl;

import fun.wsss.uio.mapper.AreaMapper;
import fun.wsss.uio.model.Area;
import fun.wsss.uio.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaMapper areaMapper;

    @Override
    public List<Area> getAllAreas() {
        return areaMapper.selectList(null);
    }

    @Override
    public Area getAreaById(Integer id) {
        return areaMapper.selectById(id);
    }
} 