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
public class CompanyCategory
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long companyCategoryId;
   @ManyToOne
   private Company company;
   @ManyToOne
   private Category category;
   @OneToMany(mappedBy = "companyCategory")
   private List<Jobs>jobsList;
}
