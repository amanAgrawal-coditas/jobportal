package com.portal.service;

import com.portal.dto.CandidateDto;
import com.portal.dto.PasswordDto;
import com.portal.entity.Candidate;
import com.portal.exception.CandidateDoesNotExistsException;

import java.util.List;

public interface CandidateService
{
    List<Candidate> getAllCandidates();
    String updateCandidate(CandidateDto candidateDto,long candidateId) throws CandidateDoesNotExistsException;
    String deleteCandid(long id) throws CandidateDoesNotExistsException;
    String updatePassword(PasswordDto passwordDto, long id) throws CandidateDoesNotExistsException;
}
