package dev.wsad.bm;

import dev.wsad.bm.core.entities.BuildingEntity;
import dev.wsad.bm.core.entities.UserEntity;
import dev.wsad.bm.core.repository.BuildingRepository;
import dev.wsad.bm.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DbDataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public void run(String... args) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        this.userRepository.save(UserEntity.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList("USER"))
                .build()
        );

        this.userRepository.save(UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList("USER", "ADMIN"))
                .build()
        );

        this.buildingRepository.save(BuildingEntity.builder()
                .name("Building 1")
                .city("Varazdin")
                .build());

        this.buildingRepository.save(BuildingEntity.builder()
                .name("Building 2")
                .city("Zagreb")
                .build());
    }
}