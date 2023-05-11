package com.portal.service;

import com.portal.entity.Candidate;
import com.portal.entity.Company;
import com.portal.repository.CandidateRepository;
import com.portal.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailService implements UserDetailsService
{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Candidate> candidate = candidateRepository.findByEmail(username);
        if (candidate.isPresent()) {
            return candidate.get();
        }
        else {
            Optional<Company> company = companyRepository.findByEmail(username);
            if (company.isPresent()) {
                   return company.get();
            }
        }
        return null;
    }
}
