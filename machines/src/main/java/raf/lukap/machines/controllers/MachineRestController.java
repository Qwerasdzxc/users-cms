package raf.lukap.machines.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import raf.lukap.machines.model.Machine;
import raf.lukap.machines.model.MachineStatus;
import raf.lukap.machines.model.User;
import raf.lukap.machines.model.UserPermissions;
import raf.lukap.machines.requests.CreateMachineRequest;
import raf.lukap.machines.services.MachineService;
import raf.lukap.machines.services.UserService;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/machines")
public class MachineRestController {

    private final MachineService machineService;
    private final UserService userService;

    public MachineRestController(MachineService machineService, UserService userService) {
        this.machineService = machineService;
        this.userService = userService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMachines(
            @RequestParam(value = "from", required = false) Optional<Long> from,
            @RequestParam(value = "to", required = false) Optional<Long> to,
            @RequestParam(value = "status", required = false) Optional<String> status,
            @RequestParam(value = "name", required = false) Optional<String> name,
            Authentication authentication) {
        final User user = userService.findByUsername(authentication.getName());
        final UserPermissions permissions = user.getPermissions();
        if (!permissions.isCanSearchMachines())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final List<Machine> machines = machineService.findByCreatedByAndActiveTrue(user);
        Iterator<Machine> i = machines.iterator();
        while (i.hasNext()) {
            Machine machine = i.next();
            if (from.isPresent()) {
                if (machine.getCreatedAt().getTime() < from.get())
                    i.remove();
            }
            if (to.isPresent()) {
                if (machine.getCreatedAt().getTime() > to.get())
                    i.remove();
            }
            if (status.isPresent()) {
                if (!machine.getStatus().toString().equals(status.get()))
                    i.remove();
            }
            if (name.isPresent()) {
                if (!machine.getName().contains(name.get()))
                    i.remove();
            }
        }
        return ResponseEntity.ok(machines);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMachineById(@PathVariable Long id, Authentication authentication) {
        final User user = userService.findByUsername(authentication.getName());
        final UserPermissions permissions = user.getPermissions();
        if (!permissions.isCanSearchMachines())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final Optional<Machine> machine = machineService.findById(id);
        if (machine.isEmpty() || !Objects.equals(machine.get().getCreatedBy().getId(), user.getId()) || !machine.get().isActive())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(machine);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMachine(@RequestBody CreateMachineRequest request, Authentication authentication) {
        final UserPermissions permissions = userService.findByUsername(authentication.getName()).getPermissions();
        if (!permissions.isCanCreateMachines())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final Machine machine = machineService.save(new Machine(
                request.getName(), userService.findByUsername(authentication.getName())
        ));

        return ResponseEntity.ok(machine);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteMachine(@PathVariable Long id, Authentication authentication) {
        final User user = userService.findByUsername(authentication.getName());
        final UserPermissions permissions = user.getPermissions();
        if (!permissions.isCanDestroyMachines())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final Optional<Machine> machine = machineService.findById(id);
        if (machine.isEmpty() || !Objects.equals(machine.get().getCreatedBy().getId(), user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (machine.get().getStatus() == MachineStatus.RUNNING)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        machine.get().setActive(false);
        machineService.save(machine.get());

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startMachine(@PathVariable Long id, @RequestParam(value = "date", required = false) Optional<Long> scheduleDate, Authentication authentication) {
        final User user = userService.findByUsername(authentication.getName());
        final UserPermissions permissions = user.getPermissions();
        if (!permissions.isCanStartMachines())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final Optional<Machine> machine = machineService.findById(id);
        if (machine.isEmpty() || !Objects.equals(machine.get().getCreatedBy().getId(), user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (machine.get().getStatus() != MachineStatus.STOPPED)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            if (scheduleDate.isEmpty())
                machineService.start(machine.get());
            else {
                Date date = new Date(scheduleDate.get());
                machineService.startScheduled(machine.get(), date);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/stop/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stopMachine(@PathVariable Long id, @RequestParam(value = "date", required = false) Optional<Long> scheduleDate, Authentication authentication) {
        final User user = userService.findByUsername(authentication.getName());
        final UserPermissions permissions = user.getPermissions();
        if (!permissions.isCanStopMachines())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final Optional<Machine> machine = machineService.findById(id);
        if (machine.isEmpty() || !Objects.equals(machine.get().getCreatedBy().getId(), user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (machine.get().getStatus() != MachineStatus.RUNNING)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            if (scheduleDate.isEmpty())
                machineService.stop(machine.get());
            else {
                Date date = new Date(scheduleDate.get());
                machineService.stopScheduled(machine.get(), date);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/restart/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restartMachine(@PathVariable Long id, @RequestParam(value = "date", required = false) Optional<Long> scheduleDate, Authentication authentication) {
        final User user = userService.findByUsername(authentication.getName());
        final UserPermissions permissions = user.getPermissions();
        if (!permissions.isCanRestartMachines())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        final Optional<Machine> machine = machineService.findById(id);
        if (machine.isEmpty() || !Objects.equals(machine.get().getCreatedBy().getId(), user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (machine.get().getStatus() != MachineStatus.RUNNING)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            if (scheduleDate.isEmpty())
                machineService.restart(machine.get());
            else {
                Date date = new Date(scheduleDate.get());
                machineService.restartScheduled(machine.get(), date);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }
}
