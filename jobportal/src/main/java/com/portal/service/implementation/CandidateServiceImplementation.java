package com.portal.service.implementation;

import com.portal.dto.CandidateDto;
import com.portal.dto.PasswordDto;
import com.portal.entity.Candidate;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.repository.CandidateRepository;
import com.portal.service.CandidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImplementation implements CandidateService
{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
    @Override
    public String updateCandidate(CandidateDto candidateDto, long candidateId) throws CandidateDoesNotExistsException {
        Candidate candidate=candidateRepository.findById(candidateId).orElseThrow(()->new CandidateDoesNotExistsException(HttpStatus.BAD_REQUEST,"The candidate does not exists"));
        if (candidateDto.getEmail()!=null&&candidate.getEmail()!=candidateDto.getEmail())
        {
            candidate.setEmail(candidateDto.getEmail());
        }
        if (candidateDto.getName()!=null&&candidate.getName()!=candidateDto.getName())
        {
            candidate.setName(candidateDto.getName());
        }
        if (candidateDto.getPhoneNumber()!=0&&candidate.getPhoneNumber()!=candidateDto.getPhoneNumber())
        {
            candidate.setPhoneNumber(candidateDto.getPhoneNumber());
        }
        if (candidateDto.getImage()!=null&&candidate.getProfileImage()!=candidateDto.getImage())
        {
            candidate.setProfileImage(candidateDto.getImage());
        }
        Candidate updatedCandidate=candidateRepository.save(candidate);
         modelMapper.map(updatedCandidate,CandidateDto.class);
         return "Candidate details have been updated";
    }
    @Override
    public String deleteCandid(long id) throws CandidateDoesNotExistsException {
        Candidate candidate=candidateRepository.findById(id).orElseThrow(()->new CandidateDoesNotExistsException(HttpStatus.BAD_REQUEST,"The candidate does not exists"));
        candidateRepository.delete(candidate);
        return "The candidate has been deleted successfully";
    }

    @Override
    public String updatePassword(PasswordDto passwordDto, long id) throws CandidateDoesNotExistsException {
        Candidate candidate=candidateRepository.findById(id).orElseThrow(()->new CandidateDoesNotExistsException(HttpStatus.BAD_REQUEST,"The candidate does not exists"));
        boolean check= passwordEncoder.matches(passwordDto.getOldPassword(), candidate.getPassword());
        if (check)
        {
            candidate.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            Candidate updatedCandidate=candidateRepository.save(candidate);
            return "password has been changed";
        }
        else if (passwordDto.getOldPassword()==passwordDto.getNewPassword())
        {
            return "the old password cannot match with the new one";
        }
        else{
            return "Entered password is incorrect";
        }
    }
}
