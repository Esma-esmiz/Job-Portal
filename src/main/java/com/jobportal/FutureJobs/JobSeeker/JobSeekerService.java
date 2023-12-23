package com.jobportal.FutureJobs.JobSeeker;

import com.jobportal.FutureJobs.JobSeeker.JobSeeker;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobSeekerService {
    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }


    public List<JobSeeker> getJobSeeker(){
        return  jobSeekerRepository.findAll();
    }

    public ResponseEntity<Object> getJobSeekerById(Long id){
        Optional<JobSeeker> jobSeeker = jobSeekerRepository.findById(id);
        if (jobSeeker.isEmpty()){
            return ResponseHandler.generateResponse("JobSeeker with id " + id + " not found!!", HttpStatus.NOT_FOUND, jobSeeker);
        }
        return ResponseHandler.generateResponse("", HttpStatus.OK, jobSeeker);
    }

    public ResponseEntity<Object> getJobSeekerByEmail(String email){
        Optional<JobSeeker> jobSeeker = jobSeekerRepository.findJobSeekerByEmail(email);
        if (jobSeeker.isEmpty()){
            return ResponseHandler.generateResponse("JobSeeker with email " + email + " not found!!", HttpStatus.NOT_FOUND, jobSeeker);
        }
        return ResponseHandler.generateResponse("", HttpStatus.OK, jobSeeker);
    }
    public String createJobSeeker(JobSeeker jobSeeker){
        if (findByEmail(jobSeeker.getEmail()).isPresent() ){
            throw new IllegalStateException("this JobSeeker is already registered!!!");
        }
        jobSeeker.setCreated_at(LocalDateTime.now());
        JobSeeker status=  jobSeekerRepository.save(jobSeeker);
        if (status!= null){
            return "JobSeeker is created successfully ";
        }
        return "JobSeeker not create plc check it!!" ;
    }

    @Transactional
    public ResponseEntity<Object> updateJobSeeker(Long id, String password){
        try {

            Optional<JobSeeker> jobSeekerStored = jobSeekerRepository.findById(id);
            if (jobSeekerStored.isEmpty()){
            return ResponseHandler.generateResponse("JobSeeker with id " + id + " not found!!", HttpStatus.NOT_FOUND, jobSeekerStored);
            };


            if (password != null && !password.isEmpty()
                    && !Objects.equals(jobSeekerStored.get().getPassword(), password)) {
                jobSeekerStored.get().setPassword(password);
                jobSeekerStored.get().setLast_modified(LocalDateTime.now());
                jobSeekerRepository.save(jobSeekerStored.get());
                return ResponseHandler.generateResponse("Information Successfully updated", HttpStatus.OK, jobSeekerStored);
            }

            return ResponseHandler.generateResponse("your password is similar with previous one!!", HttpStatus.CONFLICT, null);
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(),HttpStatus.MULTI_STATUS, null);
        }
    }

    public String deleteJobSeeker(Long id){
        boolean exits = jobSeekerRepository.existsById(id);
        if (!exits){
            throw new IllegalStateException("JobSeekeristrator did not exist");
        }
        jobSeekerRepository.deleteById(id);
        return  "JobSeeker is successfully deleted";
    }

    public Optional<JobSeeker> findByEmail(String email){
        return jobSeekerRepository.findJobSeekerByEmail(email);
    }
}
