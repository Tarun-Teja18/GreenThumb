package dev.tarun.greenthumb.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_user")     // "user" is a reserved word in Postgres, so we use app_user
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)   // no two accounts share an email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean active = false;            // defaults to inactive until verified

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role_mapping",                              // the join table's name
        joinColumns = @JoinColumn(name = "user_id"),             // FK → this entity (User)
        inverseJoinColumns = @JoinColumn(name = "role_name")     // FK → the other entity (Role)
    )
    private Set<Role> roles = new HashSet<>();
}