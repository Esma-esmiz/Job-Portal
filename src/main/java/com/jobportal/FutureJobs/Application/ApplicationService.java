package com.jobportal.FutureJobs.Application;

import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Job.Job;
import com.jobportal.FutureJobs.Job.JobRepository;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.Storage.StorageService;
import com.jobportal.FutureJobs.User.User;
import com.jobportal.FutureJobs.User.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ApplicationService {
    @Autowired
    private StorageService storageService;
    private final ApplicationRepository applicationRepository;
@Autowired
private UserRepository userRepository;
@Autowired
private JobRepository jobRepository;
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    public ResponseEntity<Object> getApplicationsOfJobSeeker(Long jobSeekerId){
        try {
            Optional<User> jobSeeker = userRepository.findById(jobSeekerId);
            if (jobSeeker.isEmpty() || !jobSeeker.get().getType().equalsIgnoreCase("jobseeker") ){
                return ResponseHandler.generateResponse("Job Seeker is not found", HttpStatus.NOT_FOUND, null);
            }
            List<Application> applications =jobSeeker.get().getApplication();
            return ResponseHandler.generateResponse("Successfully Fetch applications ", HttpStatus.OK, applications);
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public ResponseEntity<Object> getAllJobApplicationOfEmployer(Long jobId){
//        not finalized
        try {
            Optional<Job> job = jobRepository.findById(jobId);
            if (job.isEmpty()){
                return ResponseHandler.generateResponse("job is not found", HttpStatus.NOT_FOUND, null);
            }
            List<Application> applications =job.get().getApplication();
            return ResponseHandler.generateResponse("Successfully Fetch applications ", HttpStatus.OK, applications);
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    public ResponseEntity<Object> getApplicationById(Long appId){

        try {
            Optional<Application> application = applicationRepository.findById(appId);
            return application.map(value -> ResponseHandler.generateResponse("Successfully Fetch application ", HttpStatus.OK, value)).orElseGet(() -> ResponseHandler.generateResponse("Application is not found", HttpStatus.NOT_FOUND, null));
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public ResponseEntity<Object> createApplication(Long jobId, Optional<Long> jobseeker,
                                                    @Valid Application application,
                                                    MultipartFile file){
          Optional< User> user = null;
        AtomicBoolean is_applied = new AtomicBoolean(false);
        try {
            Optional<Job> job = jobRepository.findById(jobId);
            if (job.isEmpty()){
                return ResponseHandler.generateResponse("Job is not found", HttpStatus.NOT_FOUND, null);
            }
            if (jobseeker.isPresent()) {
                user = userRepository.findById(jobseeker.get());
                if (user.isPresent() && user.get().getType().equalsIgnoreCase("jobseeker")) {
                    List<Application> applications = job.get().getApplication();
                    Optional<User> finalUser = user;
                    applications.forEach((app) -> {
                        if (app.getJobSeeker().equals(finalUser.get())) {
                            is_applied.set(true);
                        }
                    });
                }else {
                    return ResponseHandler.generateResponse(" job seeker is not found !!!", HttpStatus.NOT_FOUND, null);
                }
                if (is_applied.get()){
                    return ResponseHandler.generateResponse(" already applied !!!", HttpStatus.FOUND, null);
                }else {
                    application.setJobSeeker(user.get());
                }
            }else {
                // gust user application
            }
            application.setJob(job.get());
            application.setStatus("Applied"); // Applied Interview Exam Rejected  Selected
            application.setApplication_date(LocalDateTime.now());
            application.setCreated_at(LocalDateTime.now());
            Application status = applicationRepository.save(application);

            if (file!=null && !file.isEmpty()) {
                Attachment attachment = new Attachment();
                attachment.setType("cv");
                storageService.attachFile(file, attachment, status.getId(), "application");
            }

            return ResponseHandler.generateResponse("job applied successfully" , HttpStatus.OK, status);
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
