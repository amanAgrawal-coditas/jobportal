package com.portal.dto;

import com.portal.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto
{
    private String jobTitle;
    private String jobDescription;
    private double salary;
    private List<Long> addNewLocations;
    private List<Long> removeLocations;
    private Category category;
}
