package raf.lukap.machines.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import raf.lukap.machines.model.UserPermissions;

@Repository
public interface UserPermissionsRepository extends CrudRepository<UserPermissions, Long> { }
