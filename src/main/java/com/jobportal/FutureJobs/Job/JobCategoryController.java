package com.jobportal.FutureJobs.Job;

import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.Job.JobCategoryService;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.Storage.StorageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Validated
@RestController
@RequestMapping(path = "api/v1/jobcategory")
public class JobCategoryController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    private final JobCategoryService jobCategoryService;

    public JobCategoryController(JobCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getJobCategory() {
        return jobCategoryService.getJobCategory();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getJobCategoryById(@PathVariable Long id) {
        return jobCategoryService.getJobCategoryById(id);
    }

    @GetMapping(path = "/title")
    public ResponseEntity<Object> getJobCategoryByTitle(@Length(min = 2, message = "must be at list two character") @RequestParam String title) {
        return jobCategoryService.getJobCategoryByTitle(title);
    }

    @PostMapping
    public ResponseEntity<Object> createJobCategory(@Valid @ModelAttribute JobCategory jobCategory, @RequestParam(required = false) MultipartFile file) {

        ResponseEntity<Object> status = jobCategoryService.createJobCategory(jobCategory);
        if (file != null && !file.isEmpty() && status.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
            Attachment attachment = new Attachment();
            attachment.setType("icon");
            storageService.attachFile(file, attachment, jobCategory.getId(), "job-category");
        }
        return status;
    }

    @PostMapping(path = "upload/{category}")
    public ResponseEntity<Object> uploadIcon(@RequestParam(required = true) MultipartFile file,
                                             @PathVariable(name = "category", required = true) Long category) {
        Optional<JobCategory> category1 = jobCategoryRepository.findById(category);
        if (category1.isEmpty()) {
            return ResponseHandler.generateResponse("job category not found", HttpStatus.NOT_FOUND, null);
        }
        if (!file.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setType("icon");
            return storageService.attachFile(file, attachment, category, "job-category");
        }
        return ResponseHandler.generateResponse("job category not found", HttpStatus.NOT_FOUND, null);
    }

    @PutMapping(path = "{categoryId}")
    public ResponseEntity<Object> updateJobCategory(@PathVariable Long categoryId, @Length(min = 2, message = "must be at list two character") @RequestParam(required = false) String title) {
        return jobCategoryService.updateJobCategory(categoryId, title);
    }

    @DeleteMapping(path = "categoryId")
    public ResponseEntity<Object> deleteJobCategory(@PathVariable Long categoryId) {
        return jobCategoryService.deleteJobCategory(categoryId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
