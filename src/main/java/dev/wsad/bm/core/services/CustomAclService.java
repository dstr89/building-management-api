package dev.wsad.bm.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Component;

@Component
public class CustomAclService {

    @Autowired
    private JdbcMutableAclService aclService;

    public void createAcl(Class<?> entityClass, Long entityId, String username, Permission ... permissions) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(entityClass, entityId);
        MutableAcl mutableAcl = aclService.createAcl(objectIdentity);
        PrincipalSid principalSid = new PrincipalSid(username);
        for (Permission permission : permissions) {
            mutableAcl.insertAce(mutableAcl.getEntries().size(), permission, principalSid, true);
        }
        mutableAcl.setOwner(principalSid);
        aclService.updateAcl(mutableAcl);
    }

    public void updateAcl(Class<?> entityClass, Long entityId, String username, Permission ... permissions) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(entityClass, entityId);
        MutableAcl mutableAcl = null;
        try {
            mutableAcl = (MutableAcl) aclService.readAclById(objectIdentity);
        } catch (NotFoundException e) {
            mutableAcl = aclService.createAcl(objectIdentity);
        }
        for (Permission permission : permissions) {
            mutableAcl.insertAce(mutableAcl.getEntries().size(), permission, new PrincipalSid(username), true);
        }
        aclService.updateAcl(mutableAcl);
    }

    public void deleteAcl(Class<?> entityClass, Long entityId) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(entityClass, entityId);
        aclService.deleteAcl(objectIdentity, true);
    }

}
