package rs.raf.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserPermissions {

    public UserPermissions() {}

    public UserPermissions(boolean canCreateUsers, boolean canReadUsers, boolean canUpdateUsers, boolean canDeleteUsers, User user) {
        this.canCreateUsers = canCreateUsers;
        this.canReadUsers = canReadUsers;
        this.canUpdateUsers = canUpdateUsers;
        this.canDeleteUsers = canDeleteUsers;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean canCreateUsers;

    private boolean canReadUsers;

    private boolean canUpdateUsers;

    private boolean canDeleteUsers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @JsonIgnore
    private User user;
}
