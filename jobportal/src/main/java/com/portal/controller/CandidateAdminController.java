package com.portal.controller;

import com.portal.request.CandidateDto;
import com.portal.response.CandidateResponse;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidateController")
public class CandidateAdminController
{
    @Autowired
    private CandidateService candidateService;
    @GetMapping("/allCandidates")
    public List<CandidateResponse> getAllCandidates(CandidateDto candidateDto)
    {
        return candidateService.getAllCandidates();
    }
    @DeleteMapping("/deleteCandidate/{candidateId}")
    String deleteCandidate(@PathVariable long candidateId) throws CandidateDoesNotExistsException
    {

        return candidateService.deleteCandid(candidateId);
    }
}
