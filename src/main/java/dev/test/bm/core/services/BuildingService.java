package dev.test.bm.core.services;

import java.util.List;

import dev.test.bm.core.entities.BuildingEntity;

public interface BuildingService {

    List<BuildingEntity> getAllBuildings();

    BuildingEntity getBuildingById(Long id);

    BuildingEntity createBuilding(BuildingEntity newBuilding);

    BuildingEntity updateBuilding(BuildingEntity newBuilding, Long id);

    void deleteBuilding(Long id);

}
