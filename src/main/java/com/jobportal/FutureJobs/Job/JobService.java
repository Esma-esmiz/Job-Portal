package com.jobportal.FutureJobs.Job;

import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.Storage.StorageService;
import com.jobportal.FutureJobs.User.User;
import com.jobportal.FutureJobs.User.UserRepository;
import com.jobportal.FutureJobs.User.UserService;
import jakarta.transaction.Transactional;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private StorageService storageService;
    private final JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  JobCategoryRepository jobCategoryRepository;
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public ResponseEntity<Object> getJobs(){
        List<Job> jobs = jobRepository.findAll();
        if (!jobs.isEmpty()){
            return ResponseHandler.generateResponse("Successfully Fetch All jobs ", HttpStatus.OK, jobs);
        }
        return ResponseHandler.generateResponse("job not avilable", HttpStatus.OK, null);
    }


    public ResponseEntity<Object> getJobById(Long jobId){
        Optional<Job> job = jobRepository.findById(jobId);
        if (job.isPresent()){
            return ResponseHandler.generateResponse("Successfully Fetch the job ", HttpStatus.OK, job);
        }
        return ResponseHandler.generateResponse("this job is not found", HttpStatus.NOT_FOUND, null);

    }

//    public ResponseEntity<Object> getEmployerJobById(Long employerId, Long jobId ){
//        Optional<User> user=getEmployerById(employerId);
//        Optional<Job> job = jobRepository.findById(jobId);
//
//        if (user.isEmpty() || job.isEmpty() ){
//            return ResponseHandler.generateResponse("employer or job is not found", HttpStatus.NOT_FOUND, null);
//        }
//
//        List<Job> jobs = user.get().getJob();
//        if (!jobs.isEmpty()){
//            return ResponseHandler.generateResponse("Successfully Fetch All jobs of "+user.get().getName()+" company ", HttpStatus.OK, jobs);
//        }
//        return ResponseHandler.generateResponse("error check your input", HttpStatus.MULTI_STATUS, null);
//
//    }

    public ResponseEntity<Object> getJobsByCategory(Long categoryId){
        Optional<JobCategory> category=jobCategoryRepository.findById(categoryId);
        return category.map(jobCategory -> ResponseHandler.generateResponse("Avilable jobs in this category", HttpStatus.OK,
                jobCategory.getJobs())).orElseGet(() -> ResponseHandler.generateResponse("job category is not found", HttpStatus.NOT_FOUND, null));
    }


    public ResponseEntity<Object> getJobByEmployerName(Long employerId, String companyName){
        if (employerId!=null){
            Optional<User> employer=userRepository.findById(employerId);
            return employer.map(employer1 -> ResponseHandler.generateResponse("Avilable jobs in this company id", HttpStatus.OK,
                    employer1.getJob())).orElseGet(() -> ResponseHandler.generateResponse("company is not found", HttpStatus.NOT_FOUND, null));
        } else {
            Optional<User> employer = userRepository.findUserByNameAndType(companyName, "employer");
            return employer.map(employer1 -> ResponseHandler.generateResponse("Avilable jobs in this company", HttpStatus.OK,
                    employer1.getJob())).orElseGet(() -> ResponseHandler.generateResponse("company is not found", HttpStatus.NOT_FOUND, null));
        }
    }
    public ResponseEntity<Object> getEmployerAllJobs(Long employerId){
        Optional<User> user=getEmployerById(employerId);
        if (user.isEmpty()){
            return ResponseHandler.generateResponse("employer is not found", HttpStatus.NOT_FOUND, null);
        }

        List<Job> jobs = user.get().getJob();
        if (!jobs.isEmpty()){
            return ResponseHandler.generateResponse("Successfully Fetch All jobs ", HttpStatus.OK, jobs);
        }
        return ResponseHandler.generateResponse("no job is found for "+user.get().getName()+" company", HttpStatus.NOT_FOUND, null);
    }


    public ResponseEntity<Object> createJob(Long employerId, Job job, MultipartFile attachment){
        try {
            Optional<User> user=getEmployerById(employerId);
            if (user.isEmpty() || !user.get().getType().equalsIgnoreCase("employer")){
                return ResponseHandler.generateResponse("employer is not found", HttpStatus.NOT_FOUND, null);
            }
            if (!job.getJobCategoryList().isEmpty()){
                job.getJobCategoryList().forEach((categoryId)->{
                   Optional<JobCategory> jobCategory= jobCategoryRepository.findById(categoryId);

                     jobCategory.ifPresent(category->job.getJobCategory().add(category));
                });
            }

            job.setEmployer(user.get());
            job.setStatus("Accepting"); // Accepting Closed
            job.setJobCategory(job.getJobCategory());
            job.setCreated_at(LocalDateTime.now());
            Job status = jobRepository.save(job);
            if (attachment!=null && !attachment.isEmpty()) {
                Attachment attachment1 = new Attachment();
                attachment1.setType("Job Attachment");
                storageService.attachFile(attachment, attachment1, status.getId(), "job");
            }
            return ResponseHandler.generateResponse("job is registered successfully " , HttpStatus.OK, status);

        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @Transactional
    public ResponseEntity<Object> updateJob(Long jobId, Job job, MultipartFile attachment){
        try {
            boolean is_updated = false;
            Optional<Job> storedjob1 = jobRepository.findById(jobId);
            if (storedjob1.isEmpty() && jobRepository.findJobByVacancyId(job.getVacancy_id()).isPresent()) {
                return ResponseHandler.generateResponse("Job not found or duplicate vacancy id", HttpStatus.NOT_FOUND, null);
            }
            Job storedjob = storedjob1.get();
            if (job.getTitle() != null && !job.getTitle().isEmpty() &&
                    !Objects.equals(job.getTitle(), storedjob.getTitle())) {
                storedjob.setTitle(job.getTitle());
                is_updated = true;
            }

            if (job.getVacancy_id() != null && !job.getVacancy_id().isEmpty() &&
                    !Objects.equals(job.getVacancy_id(), storedjob.getVacancy_id())) {
                storedjob.setVacancy_id(job.getVacancy_id());
                is_updated = true;
            }

            if (job.getPost_date() != null &&
                    !Objects.equals(job.getPost_date(), storedjob.getPost_date())) {
                storedjob.setPost_date(job.getPost_date());
                is_updated = true;
            }

            if (job.getDeadline() != null && !Objects.equals(job.getDeadline(), storedjob.getDeadline())) {
                storedjob.setDeadline(job.getDeadline());
                is_updated = true;
            }

            if (!Objects.equals(job.getMin_experiance(), storedjob.getMin_experiance())) {
                is_updated = true;
                storedjob.setMin_experiance(job.getMin_experiance());
            }

            if (job.getDescription() != null && !job.getDescription().isEmpty() &&
                    !Objects.equals(job.getDescription(), storedjob.getDescription())) {
                storedjob.setDescription(job.getDescription());
                is_updated = true;
            }

            if (job.getDescription() != null && !job.getDescription().isEmpty() &&
                    !Objects.equals(job.getDescription(), storedjob.getDescription())) {
                storedjob.setDescription(job.getDescription());
                is_updated = true;
            }

            if (job.getWork_place() != null && !job.getWork_place().isEmpty() &&
                    !Objects.equals(job.getWork_place(), storedjob.getWork_place())) {
                storedjob.setWork_place(job.getWork_place());
                is_updated = true;
            }

            if (job.getJob_modality() != null && !job.getJob_modality().isEmpty() &&
                    !Objects.equals(job.getJob_modality(), storedjob.getJob_modality())) {
                storedjob.setJob_modality(job.getJob_modality());
                is_updated = true;
            }

            if (job.getApplied_through() != null && !job.getApplied_through().isEmpty() &&
                    !Objects.equals(job.getApplied_through(), storedjob.getApplied_through())) {
                storedjob.setApplied_through(job.getApplied_through());
                is_updated = true;
            }

            if (is_updated) {
                storedjob.setStatus("updated");
                if (attachment!=null && !attachment.isEmpty()) {  // needs fix
                    Attachment attachment1 = new Attachment();
                    attachment1.setType("Job Attachment");
                    storageService.attachFile(attachment, attachment1, storedjob.getId(), "job");
                }
            }

            storedjob.setModified_at(LocalDateTime.now());
            return ResponseHandler.generateResponse("job updated successfully needs approval", HttpStatus.OK, storedjob);
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    private Optional<User> getEmployerById(Long employerId){
        return userRepository.findById(employerId);
    }

}
