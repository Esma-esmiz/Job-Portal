package com.jobportal.FutureJobs.Experience;

import com.jobportal.FutureJobs.Attachment.Attachment;
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
public class ExperienceService {
  @Autowired
  private StorageService storageService;
   private List<Experience> storedExperienes;
    private final ExperienceRepository experienceRepository;
    @Autowired
    private UserRepository userRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }


    public ResponseEntity<Object> getExperiencesOfJobSeeker(Long jobseekerId){
        try {
            Optional<User> jobSeeker = userRepository.findById(jobseekerId);
            if (jobSeeker.isEmpty() || !jobSeeker.get().getType().equalsIgnoreCase("JobSeeker")) {
                return ResponseHandler.generateResponse(" job Seeker is not found", HttpStatus.NOT_FOUND, null);
            }
            List<Experience> experiences = jobSeeker.get().getExperiences();
            return ResponseHandler.generateResponse("Successfully Fetch user ", HttpStatus.OK, experiences);
        }catch(Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public ResponseEntity<Object> getExperienceById(Long expId){
        return null;
    }

    public ResponseEntity<Object> createExperience(Long jobseekerId, @Valid Experience experience, MultipartFile file){
 AtomicBoolean duplicate = new AtomicBoolean(false);
        try {
            Optional<User> jobSeeker = userRepository.findById(jobseekerId);
            if (jobSeeker.isEmpty() || !jobSeeker.get().getType().equalsIgnoreCase("JobSeeker")) {
                return ResponseHandler.generateResponse(" job Seeker is not found", HttpStatus.NOT_FOUND, null);
            }
//

            storedExperienes = jobSeeker.get().getExperiences();
            storedExperienes.forEach((item)->{
              if ( item.getEmployer_company().equalsIgnoreCase(experience.getEmployer_company())
              && item.getJob_title().equalsIgnoreCase(experience.getJob_title())
              ) {
                  duplicate.set(true);
              }
            });

            if (duplicate.get()){
                return ResponseHandler.generateResponse("duplicate experience plc check company name and position title", HttpStatus.FOUND, null);
            }

            experience.setJobSeeker(jobSeeker.get());
            experience.setCreated_at(LocalDateTime.now());
            Experience status = experienceRepository.save(experience);

            if (file!=null && !file.isEmpty()) {
                Attachment attachment = new Attachment();
                attachment.setType("Experience Attachment");
                storageService.attachFile(file, attachment, status.getId(), "user");
            }
            return ResponseHandler.generateResponse("experience is created successfully" , HttpStatus.OK, status);
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
