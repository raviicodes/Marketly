package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;
    @Enumerated(EnumType.STRING)
    @Column(length = 20 , name = "role_name")
    private AppRole roleName;
    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE} ,fetch =FetchType.LAZY,mappedBy = "roles")
    private Set<User> users;
}
