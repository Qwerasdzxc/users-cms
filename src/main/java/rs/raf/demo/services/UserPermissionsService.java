package rs.raf.demo.services;

import rs.raf.demo.model.UserPermissions;
import rs.raf.demo.repositories.UserPermissionsRepository;

import java.util.List;
import java.util.Optional;

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
