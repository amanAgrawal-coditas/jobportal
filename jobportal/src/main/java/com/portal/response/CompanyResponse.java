package com.portal.response;

import com.portal.entity.ProfileImage;
import com.portal.request.ProfileImageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse
{
    private String name;
    private String email;
    private String password;
    private String description;
    private ProfileImageDto profileImage;
    private List<String> locationList;
    private List<String>categoryList;
}
