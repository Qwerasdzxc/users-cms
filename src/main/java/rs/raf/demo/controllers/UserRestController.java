package rs.raf.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.User;
import rs.raf.demo.model.UserPermissions;
import rs.raf.demo.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanReadUsers())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user, Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanCreateUsers())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User user, Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanUpdateUsers())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(userService.save(user));
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
