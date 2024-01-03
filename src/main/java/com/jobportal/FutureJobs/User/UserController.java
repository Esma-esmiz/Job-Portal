package com.jobportal.FutureJobs.User;

import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.Storage.StorageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
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
@RequestMapping(path = "api/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(){
        return  userService.getUsers();
    }

    @GetMapping
    @RequestMapping(path = "{id}")
    public ResponseEntity<Object> getUserByType(@Valid @Positive(message = "input value must be positive number") @PathVariable("id") Long id){
        return  userService.getUserById(id);
    }
    @GetMapping
    @RequestMapping(path = "/type")
    public ResponseEntity<Object> getUserByType(
            @Pattern(regexp="^[a-zA-Z]+$",message = "only words are allowed")
            @RequestParam("type") String type){
        return  userService.getUserByType(type);
    }

    @GetMapping
    @RequestMapping(path = "/email")
    public ResponseEntity<Object> getUserByEmail(
            @Email(message = "check your email")
            @RequestParam("email") String email){
        return  userService.getUserByUserEmail(email);
    }


    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        return  userService.createUser(user);
    }

    @PostMapping(path = "profile-picture/upload")
    public ResponseEntity<Object> uploadProfile( @RequestParam(name = "profile-picture", required = true) MultipartFile profile,
                                                @RequestParam(name = "user", required = true) Long user){

        if (profile!=null && !profile.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setType("profile picture");
           Optional<User> user1= userRepository.findById(user);
           if (user1.isEmpty()){
               return ResponseHandler.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
           }
            storageService.attachFile(profile, attachment, user, "user");
            return ResponseHandler.generateResponse("profile picture successfully uploaded", HttpStatus.OK, null);
        }
        return ResponseHandler.generateResponse("invalid attachment input", HttpStatus.MULTI_STATUS, null);
    }

    @PostMapping(path = "logo/upload")
    public ResponseEntity<Object> uploadLogo( @RequestParam(name = "logo", required = true) MultipartFile profile,
                                                 @RequestParam(name = "employer", required = true) Long user){

        if (profile!=null && !profile.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setType("logo");
            Optional<User> user1= userRepository.findById(user);
            if (user1.isEmpty() || !user1.get().getType().equalsIgnoreCase("employer")){
                return ResponseHandler.generateResponse("employer not found", HttpStatus.NOT_FOUND, null);
            }
            storageService.attachFile(profile, attachment, user, "user");
            return ResponseHandler.generateResponse("company logo is successfully uploaded", HttpStatus.OK, null);
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
