package com.jobportal.FutureJobs.JobSeeker;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(path = "api/v1/job-seeker")
public class JobSeekerDetailController {
    private final JobSeekerDetailService jobSeekerDetailService;

    public JobSeekerDetailController(JobSeekerDetailService jobSeekerDetailService) {
        this.jobSeekerDetailService = jobSeekerDetailService;
    }
    @GetMapping(path = "detail/{seeker}")
    public ResponseEntity<Object> getJobSeekerDetail(@Valid @PathVariable(name = "seeker") Long seekerId){
        return jobSeekerDetailService.getJobSeekerDetail(seekerId);
    }

    @PostMapping
    public ResponseEntity<Object> createJobSeekerDetail(@Valid  @RequestBody JobSeekerDetaile jobSeeker){
        return jobSeekerDetailService.createJobSeekerDetail(jobSeeker);
    }
    @PutMapping(path = "{seekerId}")
    public ResponseEntity<Object> updateJobSeekerDetail( @PathVariable Long seekerId,
                                                   @Pattern(regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
                                                   @RequestParam(required = false) String password){
        return jobSeekerDetailService.updateJobSeekerDetail(seekerId, password);
    }
    @DeleteMapping(path = "delete-detail/{seeker-detail}")
    public ResponseEntity<Object> deleteJobSeekerDetail(@PathVariable(name = "seeker-detail") Long seekerId){
        return jobSeekerDetailService.deleteJobSeekerDetail(seekerId);
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
