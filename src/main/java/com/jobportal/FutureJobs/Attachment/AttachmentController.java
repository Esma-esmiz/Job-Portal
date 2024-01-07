package com.jobportal.FutureJobs.Attachment;

import com.jobportal.FutureJobs.Storage.StorageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/v1/attach")
public class AttachmentController {
    @Autowired
    private  final  AttachmentRepository attachmentRepository;

    @Autowired
    private StorageService storageService;

    public AttachmentController(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createAttach( @RequestParam MultipartFile file, @ModelAttribute Attachment attachment){
     return    storageService.attachFile(file, attachment, (long)1, "user" );
    }
}
