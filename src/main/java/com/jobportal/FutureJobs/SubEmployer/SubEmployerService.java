package com.jobportal.FutureJobs.SubEmployer;

import com.jobportal.FutureJobs.Employeer.Employeer;
import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.User.User;
import com.jobportal.FutureJobs.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubEmployerService {
    //    2011A 2012II -->2013 accounting bsc batu, regular
    @Autowired
    private UserRepository userRepository;
    private final SubEmployerRepository subEmployerRepository;

    public SubEmployerService(SubEmployerRepository subEmployerRepository) {
        this.subEmployerRepository = subEmployerRepository;
    }

    public ResponseEntity<Object> getAllSubEmployer() {
        List<SubEmployer> subEmployers = subEmployerRepository.findAll();
        if (!subEmployers.isEmpty()) {
            return ResponseHandler.generateResponse("Successfully Fetch All Employer sub accounts ", HttpStatus.OK, subEmployers);
        }
        return ResponseHandler.generateResponse("employer sub account not found", HttpStatus.OK, null);

    }

    public ResponseEntity<Object> getSubEmployer(long subEmployerId) {
        Optional<SubEmployer> subEmployer = subEmployerRepository.findById(subEmployerId);
        if (subEmployer.isPresent()) {
            return ResponseHandler.generateResponse("Successfully Fetch Employer sub accounts ", HttpStatus.OK, subEmployer);
        }
        return ResponseHandler.generateResponse("employer sub account not found", HttpStatus.OK, null);

    }

    public ResponseEntity<Object> getSubEmployerByDepartment(String department) {
//        Optional<SubEmployer> subEmployer = subEmployerRepository.findSubEmployerByDepartment_Name(department);
//        return subEmployer.map(employer -> ResponseHandler.generateResponse("Successfully Fetch Employer sub accounts ", HttpStatus.OK, employer))
//                .orElseGet(() -> ResponseHandler.generateResponse("employer sub account not found", HttpStatus.OK, null));
return  null;
    }

    public ResponseEntity<Object> createSubEmployer(Long employerId, SubEmployer subEmployer) {
        try {
            Optional<SubEmployer> subEmployerPosted = findByEmail(subEmployer.getEmail());
            Optional<User> employer = userRepository.findById(employerId);
            if (subEmployerPosted.isPresent() || employer.isEmpty() || !employer.get().getType().equalsIgnoreCase("employer")) {
                return ResponseHandler.generateResponse("user is already registered or employer company is not found!!!", HttpStatus.FOUND, null);
            }
            //for employer attach to sub employer
            subEmployer.setEmployer(employer.get());
            subEmployer.setCreated_at(LocalDateTime.now());
            SubEmployer status = subEmployerRepository.save(subEmployer);
            return ResponseHandler.generateResponse("user is registered successfully ", HttpStatus.OK, status);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public Optional<SubEmployer> findByEmail(String email) {
        return subEmployerRepository.findSubEmployerByEmail(email);
    }

}
