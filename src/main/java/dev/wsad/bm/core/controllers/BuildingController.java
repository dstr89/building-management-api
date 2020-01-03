package dev.wsad.bm.core.controllers;

import dev.wsad.bm.core.entities.BuildingEntity;
import dev.wsad.bm.core.exceptions.BuildingNotFoundException;
import dev.wsad.bm.core.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @Transactional
    BuildingEntity create(@RequestBody BuildingEntity newBuilding, Principal principal) {
        BuildingEntity building = repository.save(newBuilding);
        return building;
    }

    @PutMapping("/buildings/{id}")
    @PreAuthorize("hasPermission(#id, 'dev.wsad.bm.core.entities.BuildingEntity', write)")
    @Transactional
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
    @PreAuthorize("hasPermission(#id, 'dev.wsad.bm.core.entities.BuildingEntity', delete)")
    @Transactional
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}