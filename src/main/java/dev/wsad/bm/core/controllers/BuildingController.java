package dev.wsad.bm.core.controllers;

import dev.wsad.bm.core.entities.BuildingEntity;
import dev.wsad.bm.core.exceptions.BuildingNotFoundException;
import dev.wsad.bm.core.repository.BuildingRepository;
import dev.wsad.bm.core.services.CustomAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private CustomAclService aclService;

    @GetMapping("/buildings")
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<BuildingEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/buildings/{id}")
    @PreAuthorize("hasPermission(#id, 'dev.wsad.bm.core.entities.BuildingEntity', read) ")
    BuildingEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BuildingNotFoundException(id));
    }

    @PostMapping("/buildings")
    @Transactional
    BuildingEntity create(@RequestBody BuildingEntity newBuilding, Principal principal) {
        BuildingEntity building = repository.save(newBuilding);
        aclService.createAcl(BuildingEntity.class, building.getId(), principal.getName(), BasePermission.READ, BasePermission.WRITE, BasePermission.DELETE);
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
        aclService.deleteAcl(BuildingEntity.class, id);
        repository.deleteById(id);
    }

}