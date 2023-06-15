package com.portal.request;
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
    private byte[] image;
    private List<Long>locationList;
    private List<Long>categoryList;
}
