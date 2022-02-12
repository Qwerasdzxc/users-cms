package raf.lukap.machines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserPermissions {

    public UserPermissions() {
    }

    public UserPermissions(boolean canCreateUsers, boolean canReadUsers, boolean canUpdateUsers, boolean canDeleteUsers, boolean canSearchMachines, boolean canStartMachines, boolean canStopMachines, boolean canRestartMachines, boolean canCreateMachines, boolean canDestroyMachines, User user) {
        this.canCreateUsers = canCreateUsers;
        this.canReadUsers = canReadUsers;
        this.canUpdateUsers = canUpdateUsers;
        this.canDeleteUsers = canDeleteUsers;
        this.canSearchMachines = canSearchMachines;
        this.canStartMachines = canStartMachines;
        this.canStopMachines = canStopMachines;
        this.canRestartMachines = canRestartMachines;
        this.canCreateMachines = canCreateMachines;
        this.canDestroyMachines = canDestroyMachines;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean canCreateUsers;

    private boolean canReadUsers;

    private boolean canUpdateUsers;

    private boolean canDeleteUsers;

    private boolean canSearchMachines;

    private boolean canStartMachines;

    private boolean canStopMachines;

    private boolean canRestartMachines;

    private boolean canCreateMachines;

    private boolean canDestroyMachines;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @JsonIgnore
    private User user;
}
