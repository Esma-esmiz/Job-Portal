package com.jobportal.FutureJobs.Job;

import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.Job.JobCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;
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
@RequestMapping(path = "api/v1/jobcategory")
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;

    public JobCategoryController(JobCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }
    @GetMapping
    public ResponseEntity<Object> getJobCategory(){
        return jobCategoryService.getJobCategory();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getJobCategoryById(@PathVariable Long id){
        return jobCategoryService.getJobCategoryById(id);
    }
    @GetMapping(path = "/title")
    public ResponseEntity<Object> getJobCategoryByTitle(@Length(min = 2, message = "must be at list two character") @RequestParam String title){
        return jobCategoryService.getJobCategoryByTitle(title);
    }
    @PostMapping
    public ResponseEntity<Object> createJobCategory(@Valid @RequestBody JobCategory jobCategory){
        return jobCategoryService.createJobCategory(jobCategory);
    }
    @PutMapping(path = "{categoryId}")
    public ResponseEntity<Object> updateJobCategory(@PathVariable Long categoryId,
                                   @Length(min = 2, message = "must be at list two character") @RequestParam(required = false) String title){
        return jobCategoryService.updateJobCategory(categoryId, title);
    }
    @DeleteMapping(path = "categoryId")
    public ResponseEntity<Object> deleteJobCategory(@PathVariable Long categoryId){
        return jobCategoryService.deleteJobCategory(categoryId);
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
