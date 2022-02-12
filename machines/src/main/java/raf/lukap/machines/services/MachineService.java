package raf.lukap.machines.services;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import raf.lukap.machines.model.*;
import raf.lukap.machines.repositories.MachineRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MachineService implements IService<Machine, Long> {

    private final MachineRepository repository;

    private final MachineOperationErrorService machineOperationErrorService;

    private final TaskScheduler taskScheduler;

    public MachineService(MachineRepository repository, MachineOperationErrorService machineOperationErrorService, TaskScheduler taskScheduler) {
        this.repository = repository;
        this.machineOperationErrorService = machineOperationErrorService;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public Machine save(Machine machine) {
        return repository.save(machine);
    }

    @Override
    public Optional<Machine> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Machine> findAll() {
        return (List<Machine>) repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Machine> findByCreatedByAndActiveTrue(User createdBy) {
        return repository.findByCreatedByAndActiveTrue(createdBy);
    }

    @Async
    public void start(Machine machine) throws InterruptedException {
        machine.setStatus(MachineStatus.BUSY);
        repository.save(machine);

        Thread.sleep(10000);

        machine.setStatus(MachineStatus.RUNNING);
        repository.save(machine);
    }

    @Async
    public void startScheduled(Machine machine, Date timestamp) {
        this.taskScheduler.schedule(() -> {
            try {
                final Machine presentTimeMachine = findById(machine.getId()).get();
                if (!presentTimeMachine.isActive())
                    throw new Exception("Machine is destroyed.");
                if (presentTimeMachine.getStatus() != MachineStatus.STOPPED)
                    throw new Exception("Machine is already running.");

                start(machine);
            } catch (Exception e) {
                MachineOperationError error = new MachineOperationError(e.getMessage(), MachineOperation.START, machine);
                machineOperationErrorService.save(error);
            }
        }, timestamp);
    }

    @Async
    public void stop(Machine machine) throws InterruptedException {
        machine.setStatus(MachineStatus.BUSY);
        repository.save(machine);

        Thread.sleep(10000);

        machine.setStatus(MachineStatus.STOPPED);
        repository.save(machine);
    }

    @Async
    public void stopScheduled(Machine machine, Date timestamp) {
        this.taskScheduler.schedule(() -> {
            try {
                final Machine presentTimeMachine = findById(machine.getId()).get();
                if (!presentTimeMachine.isActive())
                    throw new Exception("Machine is destroyed.");
                if (presentTimeMachine.getStatus() != MachineStatus.RUNNING)
                    throw new Exception("Machine is either already stopped or busy.");

                stop(machine);
            } catch (Exception e) {
                MachineOperationError error = new MachineOperationError(e.getMessage(), MachineOperation.STOP, machine);
                machineOperationErrorService.save(error);
            }
        }, timestamp);
    }

    @Async
    public void restart(Machine machine) throws InterruptedException {
        machine.setStatus(MachineStatus.BUSY);
        repository.save(machine);

        Thread.sleep(5000);

        machine.setStatus(MachineStatus.STOPPED);
        repository.save(machine);

        machine.setStatus(MachineStatus.BUSY);
        repository.save(machine);

        Thread.sleep(5000);

        machine.setStatus(MachineStatus.RUNNING);
        repository.save(machine);
    }

    @Async
    public void restartScheduled(Machine machine, Date timestamp) {
        this.taskScheduler.schedule(() -> {
            try {
                final Machine presentTimeMachine = findById(machine.getId()).get();
                if (!presentTimeMachine.isActive())
                    throw new Exception("Machine is destroyed.");
                if (presentTimeMachine.getStatus() != MachineStatus.RUNNING)
                    throw new Exception("Machine is either stopped or busy.");

                restart(machine);
            } catch (Exception e) {
                MachineOperationError error = new MachineOperationError(e.getMessage(), MachineOperation.RESTART, machine);
                machineOperationErrorService.save(error);
            }
        }, timestamp);
    }
}
