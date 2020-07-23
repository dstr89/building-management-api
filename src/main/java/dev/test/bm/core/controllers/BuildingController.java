package dev.test.bm.core.controllers;

import dev.test.bm.core.entities.BuildingEntity;
import dev.test.bm.core.services.BuildingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/buildings")
    @PreAuthorize("hasRole('USER')")
    List<BuildingEntity> all() {
        return buildingService.getAllBuildings();
    }

    @GetMapping("/buildings/{id}")
    @PreAuthorize("hasRole('USER')")
    BuildingEntity one(@PathVariable Long id) {
        return buildingService.getBuildingById(id);
    }

    @PostMapping("/buildings")
    @PreAuthorize("hasRole('ADMIN')")
    BuildingEntity create(@RequestBody BuildingEntity newBuilding) {
        return buildingService.createBuilding(newBuilding);
    }

    @PutMapping("/buildings/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    BuildingEntity replace(@RequestBody BuildingEntity updatedBuilding, @PathVariable Long id) {
        return buildingService.updateBuilding(updatedBuilding, id);
    }

    @DeleteMapping("/buildings/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void delete(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
    }

}