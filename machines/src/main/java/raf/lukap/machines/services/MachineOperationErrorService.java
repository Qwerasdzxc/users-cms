package raf.lukap.machines.services;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import raf.lukap.machines.model.MachineOperationError;
import raf.lukap.machines.model.User;
import raf.lukap.machines.repositories.MachineOperationErrorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MachineOperationErrorService implements IService<MachineOperationError, Long> {

    private final MachineOperationErrorRepository repository;

    public MachineOperationErrorService(MachineOperationErrorRepository repository) {
        this.repository = repository;
    }

    @Override
    public MachineOperationError save(MachineOperationError error) {
        return repository.save(error);
    }

    @Override
    public Optional<MachineOperationError> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<MachineOperationError> findAll() {
        return (List<MachineOperationError>) repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<MachineOperationError> findMachineOperationErrorsByUser(User user) {
        return repository.findMachineOperationErrorsByUser(user);
    }
}
