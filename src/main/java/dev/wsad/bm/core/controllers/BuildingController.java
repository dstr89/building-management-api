package dev.wsad.bm.core.controllers;

import dev.wsad.bm.core.entities.BuildingEntity;
import dev.wsad.bm.core.exceptions.BuildingNotFoundException;
import dev.wsad.bm.core.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @GetMapping("/buildings")
    List<BuildingEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/buildings/{id}")
    BuildingEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BuildingNotFoundException(id));
    }

    @PostMapping("/buildings")
    BuildingEntity create(@RequestBody BuildingEntity newBuilding) {
        return repository.save(newBuilding);
    }

    @PutMapping("/buildings/{id}")
    BuildingEntity replace(@RequestBody BuildingEntity newBuilding, @PathVariable Long id) {
        return repository.findById(id)
                .map(building -> {
                    building.setName(newBuilding.getName());
                    building.setCity(newBuilding.getCity());
                    return repository.save(building);
                })
                .orElseThrow(() -> new BuildingNotFoundException(id));
    }

    @DeleteMapping("/buildings/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}