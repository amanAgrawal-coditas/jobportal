package com.portal.dto;
import com.portal.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto
{
    private String name;
    private String email;
    private String password;
    private String description;
    private ProfileImage profileImage;
    private List<String>locationList;
    private List<String>categoryList;
    private Roles roles;
    private List<Jobs> jobsList;
    private long otp;
}
