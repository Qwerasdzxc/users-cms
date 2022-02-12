package raf.lukap.machines.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import raf.lukap.machines.model.User;
import raf.lukap.machines.model.UserPermissions;
import raf.lukap.machines.requests.CreateUserRequest;
import raf.lukap.machines.requests.UpdateUserRequest;
import raf.lukap.machines.services.UserPermissionsService;
import raf.lukap.machines.services.UserService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final UserPermissionsService userPermissionsService;
    private final PasswordEncoder passwordEncoder;

    public UserRestController(UserService userService, UserPermissionsService userPermissionsService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userPermissionsService = userPermissionsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanReadUsers())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request, Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanCreateUsers())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (userService.findByUsername(request.getUsername()) != null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final User user = userService.save(new User(
                request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getName(), request.getSurname())
        );

        final UserPermissions userPermissions = userPermissionsService.save(new UserPermissions(
                request.isCreatePermission(), request.isReadPermission(), request.isUpdatePermission(), request.isDeletePermission(), request.isCanSearchMachines(),
                request.isCanStartMachines(), request.isCanStopMachines(), request.isCanRestartMachines(), request.isCanCreateMachines(), request.isCanDestroyMachines(), user
        ));

        user.setPermissions(userPermissions);
        userService.save(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request, Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanUpdateUsers())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final User user = userService.findByUsername(request.getUsername());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setUsername(request.getUsername());
        userService.save(user);

        final UserPermissions userPermissions = userPermissionsService.findById(user.getPermissions().getId()).get();
        userPermissions.setCanReadUsers(request.isReadPermission());
        userPermissions.setCanUpdateUsers(request.isUpdatePermission());
        userPermissions.setCanDeleteUsers(request.isDeletePermission());
        userPermissions.setCanCreateUsers(request.isCreatePermission());
        userPermissionsService.save(userPermissions);

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id, Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanReadUsers())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(value = "/me")
    public ResponseEntity<User> getLoggedInUser(Authentication authentication) {
        final User user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id, Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanDeleteUsers())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
