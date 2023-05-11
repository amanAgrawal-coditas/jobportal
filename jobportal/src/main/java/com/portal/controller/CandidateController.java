package com.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CandidateController {

    @GetMapping("/can")
    public String candidate() {
        return "hello candidate";
    }

    @GetMapping("/cam")
    public String company() {
        return "hello company";
    }
}