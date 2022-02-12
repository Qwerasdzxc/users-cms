package raf.lukap.machines.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.lukap.machines.model.MachineOperationError;
import raf.lukap.machines.model.User;

import java.util.List;

@Repository
public interface MachineOperationErrorRepository extends CrudRepository<MachineOperationError, Long> {

    @Query("select moe from MachineOperationError moe where moe.machine.createdBy = :user")
    List<MachineOperationError> findMachineOperationErrorsByUser(@Param("user") User user);
}
