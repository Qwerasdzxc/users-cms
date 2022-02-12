package raf.lukap.machines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class MachineOperationError {

    public MachineOperationError() {}

    public MachineOperationError(String message, MachineOperation operation, Machine machine) {
        this.message = message;
        this.operation = operation;
        this.machine = machine;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Enumerated(EnumType.STRING)
    private MachineOperation operation;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "MACHINE_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Machine machine;
}
