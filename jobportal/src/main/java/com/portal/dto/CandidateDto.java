package com.portal.dto;


import com.portal.entity.ProfileImage;
import com.portal.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto
{
    private String name;
    private String email;
    private String password;
    private long phoneNumber;
    private ProfileImage image;
    private Roles roles;
    private List<Long> applicationList;
    private long otp;

}
