package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
      private long roleId;
    @Enumerated(EnumType.STRING)
    @Column(length = 20,name = "role_name")
      private AppRole roleName;

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
}
