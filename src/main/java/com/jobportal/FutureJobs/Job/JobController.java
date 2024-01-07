package com.jobportal.FutureJobs.Job;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "api/v1/job")
public class JobController {

    @Autowired
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<Object> getJobs(){
        return  jobService.getJobs();
    }

    @GetMapping
    @RequestMapping(path = "{jobId}")
    public ResponseEntity<Object> getJobById(@Valid @Positive(message = "input value must be positive number")
                                                 @PathVariable("jobId") Long jobId){
        return  jobService.getJobById(jobId);
    }

    @GetMapping
    @RequestMapping(path = "employer/{employerId}")
    public ResponseEntity<Object> getEmployerAllJobs(      @PathVariable Long employerId){
        return jobService.getEmployerAllJobs(employerId);
    }

    @GetMapping
    @RequestMapping(path = "category/{category}")
    public ResponseEntity<Object> getJobsByCategory(      @PathVariable(name = "category") Long categoryId){
        return jobService.getJobsByCategory(categoryId);
    }

    @GetMapping
    @RequestMapping(path = "company")
    public ResponseEntity<Object> getJobByEmployerName(      @RequestParam(name = "id", required = false) Long employerID,
                                                             @RequestParam(name = "name", required = false) String employerName){
        return  jobService.getJobByEmployerName(employerID, employerName);
    }

    @PostMapping
    @RequestMapping(path = "post/{employerId}")
    public ResponseEntity<Object> createJob(@Valid @PathVariable("employerId") Long employerId ,
                                            @Valid @ModelAttribute Job job,
                                            @RequestParam(required = false, name = "attachment") MultipartFile attachment){

        return jobService.createJob(employerId, job, attachment);
    }

    @PutMapping(path = "update/{job}")
    public  ResponseEntity<Object> updateJob(@Valid @PathVariable(name = "job") Long jobId,
                                             @Valid @ModelAttribute() Job jobFile,
                                             @RequestParam(required = false, name = "attachment") MultipartFile attachment){
        return jobService.updateJob(jobId, jobFile, attachment);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName= ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
