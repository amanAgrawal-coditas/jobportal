package com.portal.service.implementation;

import com.portal.entity.ProfileImage;
import com.portal.request.CandidateDto;
import com.portal.request.PasswordDto;
import com.portal.request.ProfileImageDto;
import com.portal.response.CandidateResponse;
import com.portal.entity.Candidate;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.repository.CandidateRepository;
import com.portal.response.Response;
import com.portal.service.CandidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<CandidateResponse> getAllCandidates() {
        return candidateRepository.findAll().stream().map(candidate -> {
            CandidateResponse candidateResponse = new CandidateResponse();
            candidateResponse.setCandidateId(candidate.getCandidateId());
            candidateResponse.setEmail(candidate.getEmail());
            candidateResponse.setName(candidate.getName());
            candidateResponse.setPhoneNumber(candidate.getPhoneNumber());
            return candidateResponse;
        }).collect(Collectors.toList());
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
        if (candidateDto.getImage()!=null&& !Objects.equals(candidate.getProfileImage(), candidateDto.getImage()))
        {
            ProfileImage image=modelMapper.map(candidateDto.getImage(), ProfileImage.class);
            candidate.setProfileImage(image);
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
        boolean check= passwordEncoder.matches(passwordDto.getOldPassword(), candidate.getPassword())&&!Objects.equals(passwordDto.getOldPassword(), passwordDto.getNewPassword());
        if (check)
        {
            candidate.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            Candidate updatedCandidate=candidateRepository.save(candidate);
            return "password has been changed";
        }
        else if (Objects.equals(passwordDto.getOldPassword(), passwordDto.getNewPassword()))
        {
            return "the old password cannot match with the new one";
        }
        else{
            return "Entered password is incorrect";
        }
    }

    @Override
    public Response getCandidate(String email) throws CandidateDoesNotExistsException {
        Response response=new Response();
        Candidate candidate=candidateRepository.findByEmail(email).orElseThrow(()->new CandidateDoesNotExistsException(HttpStatus.BAD_REQUEST,"The candidate does not exists"));
        response.setEmail(candidate.getEmail());
        response.setImage(candidate.getProfileImage().getImageData());
        response.setName(candidate.getName());
        response.setPhoneNumber(candidate.getPhoneNumber());
        return response;
    }
}
