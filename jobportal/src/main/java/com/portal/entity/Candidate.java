package com.portal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Candidate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long candidateId;
    private String name;
    private String email;
    private String password;
    private long phoneNumber;
    @OneToMany(mappedBy = "candidate")
    private List<AppliedApplication> applicationList;
    @OneToOne
    private Roles roles;
    @OneToMany(mappedBy = "candidate")
    private List<CandidateCategories> candidateCategoriesList;
    @OneToOne(cascade=CascadeType.ALL)
    private ProfileImage profileImage;

}
