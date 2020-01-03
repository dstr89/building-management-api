package dev.wsad.bm;

import dev.wsad.bm.core.entities.BuildingEntity;
import dev.wsad.bm.core.entities.UserEntity;
import dev.wsad.bm.core.repository.BuildingRepository;
import dev.wsad.bm.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.authentication.AuthenticationManager;
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
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserEntity user = createUser("user", "Ry6zNwqNW+59+tpC", Collections.singletonList("ROLE_USER"));
        UserEntity admin = createUser("admin", "!8hn}BUrpcxU/!4{", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));

        BuildingEntity buildingOne = createBuilding("Building 1", "Varazdin");
        BuildingEntity buildingTwo = createBuilding("Building 2", "Zagreb");
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
}