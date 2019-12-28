package dev.wsad.bm.core.repository;

import dev.wsad.bm.core.entities.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {

}
