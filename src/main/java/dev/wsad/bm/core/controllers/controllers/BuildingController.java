package dev.wsad.bm.core.controllers.controllers;

import dev.wsad.bm.core.controllers.entities.BuildingEntity;
import dev.wsad.bm.core.controllers.exceptions.BuildingNotFoundException;
import dev.wsad.bm.core.controllers.repository.BuildingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BuildingController {

    private final BuildingRepository repository;

    BuildingController(BuildingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/buildings")
    List<BuildingEntity> all() {
        return repository.findAll();
    }

    @PostMapping("/buildings")
    BuildingEntity create(@RequestBody BuildingEntity newBuilding) {
        return repository.save(newBuilding);
    }

    @GetMapping("/buildings/{id}")
    BuildingEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BuildingNotFoundException(id));
    }

    @PutMapping("/buildings/{id}")
    BuildingEntity replace(@RequestBody BuildingEntity newBuilding, @PathVariable Long id) {
        return repository.findById(id)
                .map(building -> {
                    building.setName(newBuilding.getName());
                    building.setCity(newBuilding.getCity());
                    return repository.save(building);
                })
                .orElseGet(() -> {
                    newBuilding.setId(id);
                    return repository.save(newBuilding);
                });
    }

    @DeleteMapping("/buildings/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}