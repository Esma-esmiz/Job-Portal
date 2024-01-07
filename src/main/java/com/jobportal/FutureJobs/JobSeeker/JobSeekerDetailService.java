package com.jobportal.FutureJobs.JobSeeker;

import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.User.User;
import com.jobportal.FutureJobs.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobSeekerDetailService {
    @Autowired
    private UserRepository userRepository;
    private final JobSeekerDetaileRepository jobSeekerDetaileRepository;

    public JobSeekerDetailService(JobSeekerDetaileRepository jobSeekerDetaileRepository) {
        this.jobSeekerDetaileRepository = jobSeekerDetaileRepository;
    }


    public ResponseEntity<Object> getJobSeekerDetail(Long seekerid) {
        Optional<User> jobSeeker = userRepository.findById(seekerid);
        if (jobSeeker.isEmpty() || !jobSeeker.get().getType().equalsIgnoreCase("JobSeeker")) {
            return ResponseHandler.generateResponse("Job Seeker  not found!!", HttpStatus.NOT_FOUND, null);
        }
        JobSeekerDetaile seekerDetaile = jobSeeker.get().getJobSeekerDetaile();
        return ResponseHandler.generateResponse("", HttpStatus.OK, jobSeeker);
    }

    public ResponseEntity<Object> createJobSeekerDetail(JobSeekerDetaile jobSeekerDetaile) {
//        if (findByEmail(jobSeeker.getEmail()).isPresent() ){
//            throw new IllegalStateException("this JobSeeker is already registered!!!");
//        }
//        jobSeeker.setCreated_at(LocalDateTime.now());
//        JobSeeker status=  jobSeekerRepository.save(jobSeeker);
//        if (status!= null){
//            return "JobSeeker is created successfully ";
//        }
        return null;
    }

    @Transactional
    public ResponseEntity<Object> updateJobSeekerDetail(Long id, String password) {
        try {

            Optional<JobSeekerDetaile> jobSeekerStored = jobSeekerDetaileRepository.findById(id);
            if (jobSeekerStored.isEmpty()) {
                return ResponseHandler.generateResponse("JobSeeker with id " + id + " not found!!", HttpStatus.NOT_FOUND, jobSeekerStored);
            }
            ;


//            if (password != null && !password.isEmpty()
//                    && !Objects.equals(jobSeekerStored.get().getPassword(), password)) {
//                jobSeekerStored.get().setPassword(password);
//                jobSeekerStored.get().setLast_modified(LocalDateTime.now());
//                jobSeekerRepository.save(jobSeekerStored.get());
//                return ResponseHandler.generateResponse("Information Successfully updated", HttpStatus.OK, jobSeekerStored);
//            }

            return ResponseHandler.generateResponse("your password is similar with previous one!!", HttpStatus.CONFLICT, null);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public ResponseEntity<Object> deleteJobSeekerDetail(Long id) {
        try {
//            boolean exits = jobSeekerDetaileRepository.existsById(id);
//            if (!exits) {
//                throw new IllegalStateException("JobSeeker detail not found");
//            }

            Optional<User> user= userRepository.findById(id);
            jobSeekerDetaileRepository.deleteAll( );
            jobSeekerDetaileRepository.deleteById(id);
            return ResponseHandler.generateResponse("job seeker detail is successfully deleted", HttpStatus.CONFLICT, null);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
