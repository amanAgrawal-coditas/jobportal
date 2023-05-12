package com.portal.controller;

import com.portal.dto.CandidateDto;
import com.portal.dto.PasswordDto;
import com.portal.entity.Candidate;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CandidateController
{
    @Autowired
private CandidateService candidateService;
    @PutMapping(value = "/updateCandidate/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateCandidate(@RequestBody CandidateDto candidateDto,@PathVariable long id) throws CandidateDoesNotExistsException
    {
       return candidateService.updateCandidate(candidateDto,id);
    }
    @PutMapping("/updatePassword/{id}")
    public String updatePassword(@PathVariable long id, @RequestBody PasswordDto passwordDto) throws CandidateDoesNotExistsException {
        return candidateService.updatePassword(passwordDto,id);
    }

}