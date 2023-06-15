package com.portal.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
class SalaryFilter
{
    private double minSalary;
    private double maxSalary;
}
@Data
@AllArgsConstructor
public class JobFilterDto
{
    private List<Long> companyFilterList;
    private SalaryFilter salaryFilter;
    private List<Long> locationFilterList;
    private List<Long> categoryFilterList;
    private List<String> titleFilterList;
}
