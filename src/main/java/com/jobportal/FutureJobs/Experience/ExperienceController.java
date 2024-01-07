package com.jobportal.FutureJobs.Experience;

import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Experience.Experience;
import com.jobportal.FutureJobs.Experience.ExperienceService;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.User.User;
import com.jobportal.FutureJobs.User.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/experience")
public class ExperienceController {
    @Autowired
    private final ExperienceService experienceService;

    @Autowired
    private UserRepository userRepository;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }


    @GetMapping
    public ResponseEntity<Object> getExperiencesOfJobSeeker(@RequestParam("jobseekerId") Long jobseekerId){
        return experienceService.getExperiencesOfJobSeeker(jobseekerId);
    }

    @GetMapping
    @RequestMapping(path = "{expId}")
    public ResponseEntity<Object> getExperienceById(@PathVariable("expId") Long expId){
        return experienceService.getExperienceById(expId);
    }

    @PostMapping
    public ResponseEntity<Object> createExperience(@RequestParam("jobseekerId") Long jobseekerId,
                                                   @Valid @RequestBody Experience experience,
                                                   @RequestParam(name = "attachment", required = false)MultipartFile file){

        return experienceService.createExperience(jobseekerId,experience, file);
    }

    @PostMapping(path = "update/{jobseeker}")
    public ResponseEntity<Object> updateExperience(@PathVariable(name = "jobseeker") Long jobseeker,
                                                   @RequestBody Experience experience,
                                                   @RequestParam(name = "attachment", required = false)MultipartFile file){
        return experienceService.updateExperience(jobseeker, experience, file);
    }

    @DeleteMapping(path = "delete")
    public ResponseEntity<Object> deleteExperience(@PathVariable(name = "delete") Long experienceId){
        return  experienceService.deleteExperience(experienceId);
    }
}
