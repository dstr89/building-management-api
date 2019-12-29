package dev.wsad.bm.core.controllers;

import dev.wsad.bm.core.entities.BuildingEntity;
import dev.wsad.bm.core.exceptions.BuildingNotFoundException;
import dev.wsad.bm.core.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @GetMapping("/api/user/buildings")
    List<BuildingEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/api/user/buildings/{id}")
    BuildingEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BuildingNotFoundException(id));
    }

    @PostMapping("/api/user/buildings")
    BuildingEntity create(@RequestBody BuildingEntity newBuilding) {
        return repository.save(newBuilding);
    }

    @PutMapping("/api/user/buildings/{id}")
    BuildingEntity replace(@RequestBody BuildingEntity newBuilding, @PathVariable Long id) {
        return repository.findById(id)
                .map(building -> {
                    building.setName(newBuilding.getName());
                    building.setCity(newBuilding.getCity());
                    return repository.save(building);
                })
                .orElseThrow(() -> new BuildingNotFoundException(id));
    }

    @DeleteMapping("/api/user/buildings/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}