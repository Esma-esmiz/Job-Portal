package com.jobportal.FutureJobs.Attachment;

import com.jobportal.FutureJobs.Application.ApplicationRepository;
import com.jobportal.FutureJobs.Job.JobCategoryRepository;
import com.jobportal.FutureJobs.Job.JobRepository;
import com.jobportal.FutureJobs.JobSeeker.JobSeekerDetaileRepository;
import com.jobportal.FutureJobs.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    //     @Autowired
//    private ExperienceRepository experienceRepository;
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private JobSeekerDetaileRepository jobSeekerDetaileRepository;

//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private SubEmployerRepository subEmployerRepository;


    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public ResponseEntity<Object> getAttachmentOf(String table, Long ownerId, String type) {
        return null;
    }

    public ResponseEntity<Object> createAttachment(String table, Long ownerId, Attachment attachment) {
        return null;
    }

    public ResponseEntity<Object> updateAttachment(Long attchId, Attachment newAttachment) {
        return null;
    }

    public ResponseEntity<Object> delete(Long attchId) {
        return null;
    }
}
