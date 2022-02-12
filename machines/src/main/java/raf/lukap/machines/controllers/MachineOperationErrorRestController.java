package raf.lukap.machines.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.lukap.machines.services.MachineOperationErrorService;
import raf.lukap.machines.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/operation-errors")
public class MachineOperationErrorRestController {

    private final UserService userService;
    private final MachineOperationErrorService service;

    public MachineOperationErrorRestController(UserService userService, MachineOperationErrorService service) {
        this.userService = userService;
        this.service = service;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        return ResponseEntity.ok(service.findMachineOperationErrorsByUser(userService.findByUsername(authentication.getName())));
    }
}
