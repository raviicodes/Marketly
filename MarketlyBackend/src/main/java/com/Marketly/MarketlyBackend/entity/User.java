package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "email")
}
)
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @NotBlank(message = "username can't be blank")
    @Size(min = 3,message = "username length should be greater than or equal to 3")
    private String userName;
    @NotBlank(message = "password can't be empty")
    @Size(min=6,message = "min password length should be 6")
    private String password;
    @Email(message = "email should be valid")
    private String email;
    public User(String userName,String email, String password) {
        this.password = password;
        this.email = email;
        this.userName = userName;
    }
    @ToString.Exclude
    @ManyToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE},
     fetch = FetchType.EAGER
    )
    @JoinTable(name = "user_role",
             joinColumns =  @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name="role_id")
    )
    Set<Role> role=new HashSet<>();
    @ToString.Exclude
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true
    )
    private Set<Product>products=new HashSet<>();
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
     @JoinTable( name = "user_address",
              joinColumns = @JoinColumn(name="user_id"),
             inverseJoinColumns = @JoinColumn(name = "address_id")
     )
    List<Address> addresses=new ArrayList<>();

}
