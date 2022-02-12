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
public class Machine {

    public Machine() {}

    public Machine(String name, User createdBy) {
        this.name = name;
        this.createdBy = createdBy;
        this.active = true;
        this.status = MachineStatus.STOPPED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean active;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private MachineStatus status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @JsonIgnore
    private User createdBy;
}
