package raf.lukap.machines.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import raf.lukap.machines.model.Machine;
import raf.lukap.machines.model.User;

import java.util.List;

@Repository
public interface MachineRepository extends CrudRepository<Machine, Long> {

    public List<Machine> findByCreatedByAndActiveTrue(User createdBy);
}
