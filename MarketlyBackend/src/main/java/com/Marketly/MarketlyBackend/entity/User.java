package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "email")
})
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Size(max = 20)
    @NotBlank
    private String userName;
    @Email
    @NotBlank
    @Size(max=20)
    private String email;
    @NotBlank
    @Size(max = 15)
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    fetch = FetchType.LAZY)
            @JoinTable(
                    name = "userRoles",
                  joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    Set<Role> roles=new HashSet<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    Set<Product>products=new HashSet<>();
 @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "user_address",
     joinColumns =    @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses=new ArrayList<>();

}
