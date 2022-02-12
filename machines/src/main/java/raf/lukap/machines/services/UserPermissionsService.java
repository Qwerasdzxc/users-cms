package raf.lukap.machines.services;

import org.springframework.stereotype.Service;
import raf.lukap.machines.model.UserPermissions;
import raf.lukap.machines.repositories.UserPermissionsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserPermissionsService implements IService<UserPermissions, Long> {

    private final UserPermissionsRepository repository;

    public UserPermissionsService(UserPermissionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserPermissions save(UserPermissions permissions) {
        return repository.save(permissions);
    }

    @Override
    public Optional<UserPermissions> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<UserPermissions> findAll() {
        return (List<UserPermissions>) repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
