package com.portal.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobDto
{
    private String name;
    private String email;
    private String description;
    private double expectedSalary;
    private String status;
}
