package com.portal.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordVerifyDto
{
    private String email;
    private String newPassword;
    private String reTypePassword;
}
