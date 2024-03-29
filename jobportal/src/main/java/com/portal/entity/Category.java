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
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;
    private String categoryName;
    @OneToMany(mappedBy = "category")
    private List<CandidateCategories> candidateCategoriesList;
    @OneToMany(mappedBy ="category")
    private List<Jobs> jobsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<CompanyCategory> categoryList;
}