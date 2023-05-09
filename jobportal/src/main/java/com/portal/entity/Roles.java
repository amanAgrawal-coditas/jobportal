package com.portal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Roles
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    private String name;
    @OneToOne(mappedBy = "roles")
    private Candidate candidate;
    @OneToOne(mappedBy = "role")
    private Company company;

}
