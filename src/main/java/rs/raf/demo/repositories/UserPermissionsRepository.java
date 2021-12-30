package rs.raf.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.UserPermissions;

@Repository
public interface UserPermissionsRepository extends CrudRepository<UserPermissions, Long> { }
