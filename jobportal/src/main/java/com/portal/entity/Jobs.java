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
public class Jobs
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jobId;
    private String jobTitle;
    private String jobDescription;
    private double salary;
    @OneToMany(mappedBy = "jobs")
    private List<Location> location;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "jobs")
    private List<AppliedApplication> applicationList;
    @ManyToOne
    private CompanyCategory companyCategory;


}
