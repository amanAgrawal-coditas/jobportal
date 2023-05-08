package com.portal.dto;

import com.portal.entity.Location;
import com.portal.entity.ProfileImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSignupDto
{
    private String name;
    private String email;
    private String password;
    private long phoneNumber;
    private ProfileImage image;
    private List<Location> locationList;

}
