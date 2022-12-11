package com.nure.ua.Volunteering_UA.model.user;


import com.nure.ua.Volunteering_UA.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_name", unique = true, nullable = false)
    @Size(min = 2, message = "Username should be unique and with length more then 2")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToOne
    private Role role;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @OneToOne(mappedBy = "org_admin")
    private Organization organization;


}
