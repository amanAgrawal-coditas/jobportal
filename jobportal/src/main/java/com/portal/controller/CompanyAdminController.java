package com.portal.controller;

import com.portal.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companyAdmin")
public class CompanyAdminController
{
    @Autowired
    private CompanyService companyService;

}
