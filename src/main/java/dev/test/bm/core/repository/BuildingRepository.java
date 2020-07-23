package dev.test.bm.core.repository;

import dev.test.bm.core.entities.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {

}
