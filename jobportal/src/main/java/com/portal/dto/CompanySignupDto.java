package com.portal.dto;
import com.portal.entity.Category;
import com.portal.entity.Location;
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
public class CompanySignupDto
{
    private String name;
    private String email;
    private String password;
    private String description;
    private ProfileImage profileImage;
    private List<Location>locationList;
    private List<Category>categoryList;
    private Roles roles;
}
