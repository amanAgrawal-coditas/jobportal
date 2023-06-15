package com.portal.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse
{
    private long candidateId;
    private String name;
    private String email;
    private long phoneNumber;

}