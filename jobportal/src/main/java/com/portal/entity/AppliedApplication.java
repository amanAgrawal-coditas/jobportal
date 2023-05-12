package com.portal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AppliedApplication
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appliedId;
    private String name;
    private String email;
    private String description;
    private double expectedSalary;
    private String status= JobStatus.STATUS_REJECTED.toString();
    @ManyToOne
    @JsonIgnore
    private Candidate candidate;
    @ManyToOne
    @JsonIgnore
    private Jobs jobs;


}
