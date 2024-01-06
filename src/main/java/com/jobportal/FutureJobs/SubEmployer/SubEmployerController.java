package com.jobportal.FutureJobs.SubEmployer;

import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.Storage.StorageService;
import com.jobportal.FutureJobs.User.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "api/v1/employer/subaccount")
public class SubEmployerController {
    @Autowired
    private SubEmployerRepository subEmployerRepository;
    @Autowired
    private StorageService storageService;

    @Autowired
    private final SubEmployerService subEmployerService;

    public SubEmployerController(SubEmployerService subEmployerService) {
        this.subEmployerService = subEmployerService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllSubEmployer() {
        return subEmployerService.getAllSubEmployer();
    }

    @GetMapping
    @RequestMapping(path = "/{subEmployer}")
    public ResponseEntity<Object> getSubEmployerById(@PathVariable(name = "subEmployer") Long subemployer) {
        return subEmployerService.getSubEmployerByID(subemployer);
    }

    @GetMapping
    @RequestMapping(path = "/account/{employer}")
    public ResponseEntity<Object> getSubEmployerOfEmployer(@PathVariable(name = "employer") Long employerId) {
        return subEmployerService.getSubEmployerOfEmployer(employerId);
    }

    @PostMapping
    public ResponseEntity<Object> createSubEmployer(@RequestParam(name = "employer") Long employer,
                                                    @Valid @ModelAttribute SubEmployer subEmployer,
                                                    @RequestParam("profile") MultipartFile profile) {
        return subEmployerService.createSubEmployer(employer, subEmployer, profile);
    }

    @PostMapping(path = "profile-picture/upload")
    public ResponseEntity<Object> uploadProfile( @RequestParam(name = "profile-picture", required = true) MultipartFile profile,
                                                 @RequestParam(name = "user", required = true) Long user){

        if (profile!=null && !profile.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setType("profile picture");
            Optional<SubEmployer> user1= subEmployerRepository.findById(user);
            if (user1.isEmpty()){
                return ResponseHandler.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
            }
            storageService.attachFile(profile, attachment, user, "sub_employer");
            return ResponseHandler.generateResponse("profile picture successfully uploaded", HttpStatus.OK, null);
        }
        return ResponseHandler.generateResponse("invalid attachment input", HttpStatus.MULTI_STATUS, null);
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
