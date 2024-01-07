package com.jobportal.FutureJobs.Experience;

import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.Storage.StorageService;
import com.jobportal.FutureJobs.User.User;
import com.jobportal.FutureJobs.User.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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


    public ResponseEntity<Object> getExperiencesOfJobSeeker(Long jobseekerId) {
        try {
            Optional<User> jobSeeker = userRepository.findById(jobseekerId);
            if (jobSeeker.isEmpty() || !jobSeeker.get().getType().equalsIgnoreCase("JobSeeker")) {
                return ResponseHandler.generateResponse(" job Seeker is not found", HttpStatus.NOT_FOUND, null);
            }
            List<Experience> experiences = jobSeeker.get().getExperiences();
            return ResponseHandler.generateResponse("Successfully Fetch user ", HttpStatus.OK, experiences);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public ResponseEntity<Object> getExperienceById(Long expId) {
        return null;
    }

    public ResponseEntity<Object> createExperience(Long jobseekerId, @Valid Experience experience, MultipartFile file) {
        AtomicBoolean duplicate = new AtomicBoolean(false);
        try {
            Optional<User> jobSeeker = userRepository.findById(jobseekerId);
            if (jobSeeker.isEmpty() || !jobSeeker.get().getType().equalsIgnoreCase("JobSeeker")) {
                return ResponseHandler.generateResponse(" job Seeker is not found", HttpStatus.NOT_FOUND, null);
            }
//

            storedExperienes = jobSeeker.get().getExperiences();
            storedExperienes.forEach((item) -> {
                if (item.getEmployer_company().equalsIgnoreCase(experience.getEmployer_company())
                        && item.getJob_title().equalsIgnoreCase(experience.getJob_title())
                ) {
                    duplicate.set(true);
                }
            });

            if (duplicate.get()) {
                return ResponseHandler.generateResponse("duplicate experience plc check company name and position title", HttpStatus.FOUND, null);
            }

            experience.setJobSeeker(jobSeeker.get());
            experience.setCreated_at(LocalDateTime.now());
            Experience status = experienceRepository.save(experience);

            if (file != null && !file.isEmpty()) {
                Attachment attachment = new Attachment();
                attachment.setType("Experience Attachment");
                storageService.attachFile(file, attachment, status.getId(), "user");
            }
            return ResponseHandler.generateResponse("experience is created successfully", HttpStatus.OK, status);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @Transactional
    public ResponseEntity<Object> updateExperience(Long experienceId, Experience experience, MultipartFile file) {
        try {
            Optional<Experience> experience1 = experienceRepository.findById(experienceId);
            if (experience1.isEmpty()) {
                return ResponseHandler.generateResponse("experience is not found", HttpStatus.NOT_FOUND, null);
            }

            if (experience.getEmployer_company() != null && !experience.getEmployer_company().isEmpty()
                    && !Objects.equals(experience.getEmployer_company(), experience1.get().getEmployer_company())) {

                experience1.get().setEmployer_company(experience.getEmployer_company());
            }


            if (experience.getJob_title() != null && !experience.getJob_title().isEmpty()
                    && !Objects.equals(experience.getJob_title(), experience1.get().getJob_title())) {

                experience1.get().setJob_title(experience.getJob_title());
            }

            if (experience.getStart_date() != null
                    && !Objects.equals(experience.getStart_date(), experience1.get().getStart_date())) {

                experience1.get().setStart_date(experience.getStart_date());
            }

            if (experience.getEnd_date() != null
                    && !Objects.equals(experience.getEnd_date(), experience1.get().getEnd_date())) {

                experience1.get().setEnd_date(experience.getEnd_date());
            }

            if (!Objects.equals(experience.getGross_salary(), experience1.get().getGross_salary()))  // need condition like above
            {
                experience1.get().setGross_salary(experience.getGross_salary());
            }

            if (experience.getSkills() != null && !experience.getSkills().isEmpty()
                    && !Objects.equals(experience.getSkills(), experience1.get().getSkills())) {

                experience1.get().setSkills(experience.getSkills());
            }

            if (experience.getDescription() != null && !experience.getDescription().isEmpty()
                    && !Objects.equals(experience.getDescription(), experience1.get().getDescription())) {

                experience1.get().setDescription(experience.getDescription());
            }
            experience1.get().setModified_at(LocalDateTime.now());
            if (file != null && !file.isEmpty()) {
                Attachment attachment = new Attachment();
                attachment.setType("Experience Attachment");
                storageService.attachFile(file, attachment, experience1.get().getId(), "user");
            }
            return ResponseHandler.generateResponse("experience is updated successfully", HttpStatus.OK, experience1);

        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public ResponseEntity<Object> deleteExperience(Long experienceId) {
        try {
            Optional<Experience> experience1 = experienceRepository.findById(experienceId);
            if (experience1.isEmpty()) {
                return ResponseHandler.generateResponse("experience is not found", HttpStatus.NOT_FOUND, null);
            }
            experienceRepository.deleteById(experienceId);
            return ResponseHandler.generateResponse("experience is deleted", HttpStatus.OK, null);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
