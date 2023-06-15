package com.portal.security;

import com.portal.entity.ProfileImage;
import com.portal.request.ProfileImageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse
{
    private long id;
    private String token;
    private String role;
    private String username;
    private byte[] image;
}