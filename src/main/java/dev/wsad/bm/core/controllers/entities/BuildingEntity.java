package dev.wsad.bm.core.controllers.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class BuildingEntity {

    private @Id @GeneratedValue Long id;
    private String name;
    private String city;

    public BuildingEntity(String name, String city) {
        this.name = name;
        this.city = city;
    }

}