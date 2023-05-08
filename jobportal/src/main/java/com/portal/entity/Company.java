package com.portal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;
import java.util.Set;

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
    @OneToMany(mappedBy = "company")
    private List<Location> locations;
    @OneToMany(mappedBy = "company")
    private List<Jobs> jobsList;
    @OneToOne(mappedBy = "company")
    private Roles role;
    @OneToOne
    private ProfileImage profileImage;
    @OneToMany(mappedBy = "company")
    private List<CompanyCategory> companyCategoryList;
}
