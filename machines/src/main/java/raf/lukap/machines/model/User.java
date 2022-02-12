package raf.lukap.machines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    public User() {}

    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPermissions permissions;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Machine> machines;
}
