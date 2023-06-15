package com.portal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse
{
    private String jobTitle;
    private String jobDescription;
    private double salary;
    private String company;
    private List<String> location;
}
