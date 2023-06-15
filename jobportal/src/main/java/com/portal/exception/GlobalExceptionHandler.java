package com.portal.exception;

import com.portal.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(CompanyAlreadyExistsException.class)
    public ResponseEntity<GeneralResponse>companyAlreadyExistsExceptionResolver(CompanyAlreadyExistsException e)
    {
        GeneralResponse generalResponse=new GeneralResponse();
        generalResponse.setCode("400");
        generalResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(generalResponse, e.getHttpStatus());
    }
    @ExceptionHandler(CompanyDoesNotExistsException.class)
    public ResponseEntity<GeneralResponse>companyDoesNotExists(CompanyDoesNotExistsException companyDoesNotExistsException)
    {
        GeneralResponse generalResponse=new GeneralResponse();
        generalResponse.setCode("400");
        generalResponse.setMessage(companyDoesNotExistsException.getMessage());
        return new ResponseEntity<>(generalResponse, companyDoesNotExistsException.getHttpStatus());
    }
    @ExceptionHandler(CandidateAlreadyExistsException.class)
    public ResponseEntity<GeneralResponse>candidateAlreadyExistsException(CandidateAlreadyExistsException candidateAlreadyExistsException)
    {
        GeneralResponse generalResponse=new GeneralResponse();
        generalResponse.setCode("400");
        generalResponse.setMessage(candidateAlreadyExistsException.getMessage());
        return new ResponseEntity<>(generalResponse, candidateAlreadyExistsException.getHttpStatus());
    }
    @ExceptionHandler(CandidateDoesNotExistsException.class)
    public ResponseEntity<GeneralResponse>candidateDoesNotExistsException(CandidateDoesNotExistsException candidateDoesNotExistsException)
    {
        GeneralResponse generalResponse=new GeneralResponse();
        generalResponse.setCode("400");
        generalResponse.setMessage(candidateDoesNotExistsException.getMessage());
        return new ResponseEntity<>(generalResponse, candidateDoesNotExistsException.getHttpStatus());
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<GeneralResponse>categoryNotFoundException(CategoryNotFoundException categoryNotFoundException)
    {
        GeneralResponse generalResponse=new GeneralResponse();
        generalResponse.setCode("400");
        generalResponse.setMessage(categoryNotFoundException.getMessage());
        return new ResponseEntity<>(generalResponse, categoryNotFoundException.getHttpStatus());
    }
    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<GeneralResponse>jobNotFoundException(JobNotFoundException jobNotFoundException)
    {
        GeneralResponse generalResponse=new GeneralResponse();
        generalResponse.setCode("400");
        generalResponse.setMessage(jobNotFoundException.getMessage());
        return new ResponseEntity<>(generalResponse, jobNotFoundException.getHttpStatus());
    }

}