package raf.lukap.machines.requests;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String username;
    private String name;
    private String surname;

    private boolean readPermission;
    private boolean createPermission;
    private boolean deletePermission;
    private boolean updatePermission;
}
