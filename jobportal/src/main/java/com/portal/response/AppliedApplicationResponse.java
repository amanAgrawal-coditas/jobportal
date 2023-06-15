package com.portal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppliedApplicationResponse
{
    private long id;
    private String name;
    private String email;
    private String description;
    private double expectedSalary;
    private List<CandidateResponse> candidateResponseList;

}
