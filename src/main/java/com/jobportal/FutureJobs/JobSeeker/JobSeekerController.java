package com.jobportal.FutureJobs.JobSeeker;

import com.jobportal.FutureJobs.Response.ResponseHandler;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.websocket.OnClose;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Validated
@RestController
@RequestMapping(path = "api/v1/jobseeker")
public class JobSeekerController {
    private final JobSeekerService jobSeekerService;

    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }
    @GetMapping
    public List<JobSeeker> getJobSeeker(){
        return jobSeekerService.getJobSeeker();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getJobSeekerById(@PathVariable Long id){return jobSeekerService.getJobSeekerById(id);}

    @GetMapping(path = "/email")
    public ResponseEntity<Object> getJobSeekerByEmail(@Email @RequestParam("email") String email){
        return jobSeekerService.getJobSeekerByEmail(email);}
    @PostMapping
    public String createJobSeeker(@Valid  @RequestBody JobSeeker jobSeeker){
        return jobSeekerService.createJobSeeker(jobSeeker);
    }
    @PutMapping(path = "{seekerId}")
    public ResponseEntity<Object> updateJobSeeker( @PathVariable Long seekerId,
                                                   @Pattern(regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
                                                   @RequestParam(required = false) String password){
        return jobSeekerService.updateJobSeeker(seekerId, password);
    }
    @DeleteMapping(path = "seekerId")
    public String deleteJobSeeker(@PathVariable Long seekerId){
        return jobSeekerService.deleteJobSeeker(seekerId);
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
