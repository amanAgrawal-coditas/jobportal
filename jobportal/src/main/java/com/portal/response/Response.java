package com.portal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response
{
    private String name;
    private String email;
    private long phoneNumber;
    private byte[] image;
}
