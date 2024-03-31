package com.adam.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "micro_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @Column(name = "ID")
    private String userId;
    @Column(name = "NAME" , length = 20)
    private String name;
    @Column(name = "EMAIL")
    private String  email;
    @Column(name = "ABOUT")
    private String  about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();


}
