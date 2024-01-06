package com.jobportal.FutureJobs.Application;

import com.jobportal.FutureJobs.Job.Job;
import com.jobportal.FutureJobs.Job.JobRepository;
import com.jobportal.FutureJobs.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/application")
public class ApplicationController {
    @Autowired
    private final ApplicationService applicationService;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<Object> getApplicationsOfJobSeeker(@RequestParam("jobseeker") Long jobseeker){
        return applicationService.getApplicationsOfJobSeeker(jobseeker);
    }
    @GetMapping
    @RequestMapping(path = "applications/{job}")
    public ResponseEntity<Object> getAllJobApplicationOfEmployer(@PathVariable("job") Long job){
        return applicationService.getAllJobApplicationOfEmployer(job);
    }

    @GetMapping
    @RequestMapping(path = "{appId}")
    public ResponseEntity<Object> getApplicationById(@PathVariable("appId") Long appId){
        return applicationService.getApplicationById(appId);
    }

    @PostMapping
    public ResponseEntity<Object> createApplication(@RequestParam("job") Long jobId,
                                                    @RequestParam( name = "jobseeker",required = false) Optional<Long> jobseeker,
                                                    @ModelAttribute Application application,
                                                    @RequestParam(required = false, name = "attachment") MultipartFile attachment){
        if (jobseeker.isPresent()){
        return applicationService.createApplication(jobId, jobseeker, application, attachment);
    }
            return applicationService.createApplication(jobId, Optional.empty(), application, attachment);
        }
}
