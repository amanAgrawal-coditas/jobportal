package com.portal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CandidateCategories
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long domainId;
    @ManyToOne
    @JsonIgnore
    private Candidate candidate;
    @ManyToOne
    private Category category;
}
