package com.portal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long companyId;
    private String name;
    private String email;
    private String password;
    private String description;
    @OneToMany(mappedBy = "company",cascade=CascadeType.ALL)
    private List<Location> locations;
    @OneToMany(mappedBy = "company", cascade =  CascadeType.ALL)
    private List<Jobs> jobsList;
    @OneToOne
    private Roles role;
    @OneToOne(cascade=CascadeType.ALL)
    private ProfileImage profileImage;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Category> categoryList;
}
