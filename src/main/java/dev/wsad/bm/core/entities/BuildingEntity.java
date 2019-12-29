package dev.wsad.bm.core.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="buildings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingEntity {

    private @Id @GeneratedValue Long id;
    private String name;
    private String city;

    public BuildingEntity(String name, String city) {
        this.name = name;
        this.city = city;
    }

}