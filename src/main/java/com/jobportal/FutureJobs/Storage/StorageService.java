package com.jobportal.FutureJobs.Storage;

import com.jobportal.FutureJobs.Application.Application;
import com.jobportal.FutureJobs.Application.ApplicationRepository;
import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Attachment.AttachmentRepository;
import com.jobportal.FutureJobs.Experience.Experience;
import com.jobportal.FutureJobs.Experience.ExperienceRepository;
import com.jobportal.FutureJobs.Job.Job;
import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.Job.JobCategoryRepository;
import com.jobportal.FutureJobs.Job.JobRepository;
import com.jobportal.FutureJobs.JobSeeker.JobSeekerDetaile;
import com.jobportal.FutureJobs.JobSeeker.JobSeekerDetaileRepository;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.SubEmployer.SubEmployer;
import com.jobportal.FutureJobs.SubEmployer.SubEmployerRepository;
import com.jobportal.FutureJobs.User.User;
import com.jobportal.FutureJobs.User.UserRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private final AttachmentRepository attachmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    private ExperienceRepository experienceRepository;
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private JobSeekerDetaileRepository jobSeekerDetaileRepository;

    //    @Autowired
//    private MessageRepository messageRepository;
    @Autowired
    private SubEmployerRepository subEmployerRepository;

    public StorageService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }


    public ResponseEntity<Object> attachFile(MultipartFile file, Attachment attachment, Long attachedToId, String attachedTo) {
        //name
//type
//file_type
//path
//status
//application
//experience
//job
//job-category
//jobseeker_detail
//message
//sub_employer
        try {

            String fileName = file.getOriginalFilename();
            assert fileName != null;
            if (fileName.isEmpty())
            {
                return ResponseHandler.generateResponse("error in your file uploading check it!!", HttpStatus.INTERNAL_SERVER_ERROR, null);
            }

            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
            fileName = dateFormat.format(date)+"-"+fileName;
            String file_type = file.getContentType();
            String path="D:\\WorkSpace\\Spring Boot\\FutureJobs-Attachment\\";
            File file1 = new File(path, fileName);
            file.transferTo(file1.toPath());
              if ( !file1.exists())
              {
                  return ResponseHandler.generateResponse("error in your file uploading check it!!", HttpStatus.INTERNAL_SERVER_ERROR, null);
              }
            attachment.setName(fileName);
            attachment.setFile_type(file_type);
            attachment.setPath(path);
            attachment.setStatus("created");
            attachment.setCreated_at(LocalDateTime.now());
            // attach to related table
            switch (attachedTo) {
                case "user":
                    Optional<User> user = userRepository.findById(attachedToId);
                    if (user.isPresent()) {
                        attachment.setUser(user.get());
                        break;
                    }
                    return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);
                case "application":
                    Optional<Application> application = applicationRepository.findById(attachedToId);
                    if (application.isPresent()) {
                        attachment.setApplication(application.get());
                        break;
                    }
                    return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);
//               case "experience":
//                   Optional<Experience> experience  = experienceRepository.findById(attachedToId);
//                   if (experience.isPresent()){
//                       attachment.setE(experience.get());
//                       break;
//
//                       return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);
                case "job":
                    Optional<Job> job = jobRepository.findById(attachedToId);
                    if (job.isPresent()) {
                        attachment.setJob(job.get());
                        break;
                    }
                    return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);

                case "job-category":
                    Optional<JobCategory> jobCategory = jobCategoryRepository.findById(attachedToId);
                    if (jobCategory.isPresent()) {
                        attachment.setJobCategory(jobCategory.get());
                        break;
                    }
                    return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);
                case "jobseeker_detail":
                    Optional<JobSeekerDetaile> jobseeker_detail = jobSeekerDetaileRepository.findById(attachedToId);
                    if (jobseeker_detail.isPresent()) {
                        attachment.setJobseeker_detail(jobseeker_detail.get());
                        break;
                    }
                    return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);
//               case  "message":
//                   Optional<Message> message  = messageRepository.findById(attachedToId);
//                   if (message.isPresent()){
//                       attachment.setMessage(message.get());
//                       break;
//                   }
//                   return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);

                case "sub_employer":
                    Optional<SubEmployer> subEmployer = subEmployerRepository.findById(attachedToId);
                    if (subEmployer.isPresent()) {
                        attachment.setSub_employer(subEmployer.get());
                        break;
                    }
                    return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);
                case "system":
                    // is system didit attach to any thing
                    break;
                default:
                    return ResponseHandler.generateResponse("user not found", HttpStatus.NOT_FOUND, null);
            }
            attachmentRepository.save(attachment);
            return ResponseHandler.generateResponse("file saved successfully", HttpStatus.OK, attachment);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
