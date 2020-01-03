package dev.wsad.bm.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.Permission;
import org.springframework.transaction.annotation.Transactional;

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

    public void createAcl(JdbcMutableAclService aclService, Permission permission, String username) {
        MutableAcl buildingOneAcl = aclService.createAcl(new ObjectIdentityImpl(BuildingEntity.class, getId()));
        buildingOneAcl.insertAce(buildingOneAcl.getEntries().size(), permission, new PrincipalSid(username), true);
        aclService.updateAcl(buildingOneAcl);
    }

}