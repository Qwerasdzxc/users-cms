package rs.raf.demo.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.*;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserPermissionsRepository userPermissionsRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, UserPermissionsRepository userPermissionsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userPermissionsRepository = userPermissionsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");

        User user1 = new User();
        user1.setUsername("user1");
        user1.setName("Student");
        user1.setSurname("Studentic");

        user1.setPassword(this.passwordEncoder.encode("user1"));
        this.userRepository.save(user1);

        UserPermissions permissions = new UserPermissions();
        permissions.setCanCreateUsers(true);
        permissions.setCanDeleteUsers(true);
        permissions.setCanUpdateUsers(true);
        permissions.setCanReadUsers(true);
        permissions.setUser(user1);
        this.userPermissionsRepository.save(permissions);

        user1.setPermissions(permissions);
        this.userRepository.save(user1);


        System.out.println("Data loaded!");
    }
}
