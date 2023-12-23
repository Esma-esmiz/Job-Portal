package com.jobportal.FutureJobs.Job;

import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobCategoryService {
    private final JobCategoryRepository jobCategoryRepository;

    public JobCategoryService(JobCategoryRepository jobCategoryRepository) {
        this.jobCategoryRepository = jobCategoryRepository;
    }

    public ResponseEntity<Object> getJobCategory(){
         List<JobCategory> categoris = jobCategoryRepository.findAll();
         if (!categoris.isEmpty()){
             return ResponseHandler.generateResponse("Successfully Fetch Job Category", HttpStatus.OK, categoris);
         }
        return ResponseHandler.generateResponse("Job Category not avilable", HttpStatus.OK, null);
    }

    public ResponseEntity<Object> getJobCategoryById(Long id){
        Optional<JobCategory> categoris = jobCategoryRepository.findById(id);
        if (categoris.isPresent()){
            return ResponseHandler.generateResponse("All Avilable Job Categories", HttpStatus.OK, categoris);
        }
        return ResponseHandler.generateResponse("Job Category not avilable", HttpStatus.OK, null);
    }

    public ResponseEntity<Object> getJobCategoryByTitle(String title){
        Optional<JobCategory> categoris = jobCategoryRepository.findJobCategoryByTitle(title);
        if (categoris.isPresent()){
            return ResponseHandler.generateResponse("Job Category with title " + title , HttpStatus.OK, categoris);
        }
        return ResponseHandler.generateResponse("Job Category with title " + title +" are not found", HttpStatus.OK, null);
    }


    public ResponseEntity<Object> createJobCategory(JobCategory jobCategory){
        try {
            Optional<JobCategory> jobCategoryStored = findByEmail(jobCategory.getTitle());
            if (jobCategoryStored.isPresent()) {
//            throw new IllegalStateException("this jobCategory is already registered!!!");
                return ResponseHandler.generateResponse("the job Category titled " + jobCategory.getTitle() + " is already registered!!!", HttpStatus.FOUND, null);
            }
            jobCategory.setCreated_at(LocalDateTime.now());
            JobCategory status = jobCategoryRepository.save(jobCategory);
                 return ResponseHandler.generateResponse("job Category is created successfully " , HttpStatus.OK, status);
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);

        }
    }

    @Transactional
    public ResponseEntity<Object> updateJobCategory(Long id, String title){
        try{
        Optional<JobCategory> jobCategoryStored = jobCategoryRepository.findById(id);
        if (jobCategoryStored.isEmpty()) {
            return ResponseHandler.generateResponse("Job Category with id " + id + " is not found!!", HttpStatus.NOT_FOUND, null);
        }
        if (title !=null && !title.isEmpty()
                && !Objects.equals(jobCategoryStored.get().getTitle(), title)){
            jobCategoryStored.get().setTitle(title);
            jobCategoryStored.get().setLast_modified(LocalDateTime.now());
            return ResponseHandler.generateResponse("Information Successfully updated", HttpStatus.OK, jobCategoryStored);
        }
            return ResponseHandler.generateResponse("Job Category title is similar with previous one!!", HttpStatus.CONFLICT, null);
    }catch (Exception ex){
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);

    }
    }

    public ResponseEntity<Object> deleteJobCategory(Long id){
        try {
            boolean exits = jobCategoryRepository.existsById(id);
            if (!exits) {
                return ResponseHandler.generateResponse("Job Category with id " + id + " not found!!", HttpStatus.NOT_FOUND, null);
            }
            jobCategoryRepository.deleteById(id);
            return ResponseHandler.generateResponse("Job Category Deleted Successfully", HttpStatus.OK, null);
        } catch (Exception ex){
                return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
            }
    }

    public Optional<JobCategory> findByEmail(String title){
        return jobCategoryRepository.findJobCategoryByTitle(title);
    }
}
