package dev.wsad.bm.core.controllers.repository;

import dev.wsad.bm.core.controllers.entities.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {

}
