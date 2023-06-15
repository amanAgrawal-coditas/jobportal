package com.portal.service;

import com.portal.request.CandidateDto;
import com.portal.request.PasswordDto;
import com.portal.response.CandidateResponse;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.response.Response;

import java.util.List;

public interface CandidateService
{
    List<CandidateResponse> getAllCandidates();
    String updateCandidate(CandidateDto candidateDto,long candidateId) throws CandidateDoesNotExistsException;
    String deleteCandid(long id) throws CandidateDoesNotExistsException;
    String updatePassword(PasswordDto passwordDto, long id) throws CandidateDoesNotExistsException;
    Response getCandidate(String email) throws CandidateDoesNotExistsException;
}
