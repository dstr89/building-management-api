package dev.wsad.bm;

import dev.wsad.bm.core.entities.BuildingEntity;
import dev.wsad.bm.core.entities.UserEntity;
import dev.wsad.bm.core.repository.BuildingRepository;
import dev.wsad.bm.core.repository.UserRepository;
import dev.wsad.bm.core.services.CustomAclService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class DbDataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private CustomAclService aclService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserEntity user = createUser("user", "Ry6zNwqNW+59+tpC", Collections.singletonList("ROLE_USER"));
        UserEntity admin = createUser("admin", "!8hn}BUrpcxU/!4{", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));

        //BuildingEntity buildingOne = createBuilding("Building 1", "Varazdin");
        //BuildingEntity buildingTwo = createBuilding("Building 2", "Zagreb");

        //authenticateAdmin(admin);
        //generateAcl(user, admin, buildingOne);
        //generateAcl(user, admin, buildingTwo);
    }

    private BuildingEntity createBuilding(String name, String city) {
        return this.buildingRepository.save(BuildingEntity.builder()
                .name(name)
                .city(city)
                .build());
    }

    private UserEntity createUser(String user, String password, List<String> role_user) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return this.userRepository.save(UserEntity.builder()
                .username(user)
                .password(passwordEncoder.encode(password))
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .isNonExpired(true)
                .isNonLocked(true)
                .roles(role_user)
                .build()
        );
    }

    private void authenticateAdmin(UserEntity admin) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(admin, "!8hn}BUrpcxU/!4{");
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
    }

    private void generateAcl(UserEntity user, UserEntity admin, BuildingEntity building) {
        aclService.deleteAcl(BuildingEntity.class, building.getId());
        aclService.createAcl(BuildingEntity.class, building.getId(), admin.getUsername(), BasePermission.READ, BasePermission.WRITE, BasePermission.DELETE);
        //aclService.updateAcl(BuildingEntity.class, building.getId(), user.getUsername(), BasePermission.READ);
    }
}