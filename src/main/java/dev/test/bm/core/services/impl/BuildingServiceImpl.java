package dev.test.bm.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.test.bm.core.entities.BuildingEntity;
import dev.test.bm.core.exceptions.BuildingNotFoundException;
import dev.test.bm.core.repository.BuildingRepository;
import dev.test.bm.core.services.BuildingService;

@Component
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository repository;

    @Override
    public List<BuildingEntity> getAllBuildings() {
        return repository.findAll();
    }

    @Override
    public BuildingEntity getBuildingById(final Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new BuildingNotFoundException(id));
    }

    @Override
    public BuildingEntity createBuilding(final BuildingEntity newBuilding) {
        return repository.save(newBuilding);
    }

    @Override
    public BuildingEntity updateBuilding(final BuildingEntity newBuilding, final Long id) {
        return repository.findById(id)
                         .map(building -> {
                             building.setName(newBuilding.getName());
                             building.setCity(newBuilding.getCity());
                             return repository.save(building);
                         })
                         .orElseThrow(() -> new BuildingNotFoundException(id));
    }

    @Override
    public void deleteBuilding(final Long id) {
        repository.deleteById(id);
    }

}
