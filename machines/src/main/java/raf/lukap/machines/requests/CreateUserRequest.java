package raf.lukap.machines.requests;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String username;
    private String password;
    private String name;
    private String surname;

    private boolean readPermission;
    private boolean createPermission;
    private boolean deletePermission;
    private boolean updatePermission;

    private boolean canSearchMachines;
    private boolean canStartMachines;
    private boolean canStopMachines;
    private boolean canRestartMachines;
    private boolean canCreateMachines;
    private boolean canDestroyMachines;
}
