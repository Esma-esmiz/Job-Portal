package com.jobportal.FutureJobs.User;

import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.Job.JobCategoryRepository;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private JobCategoryRepository jobCategoryRepository;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> getUsers(){
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()){
            return ResponseHandler.generateResponse("Successfully Fetch All User ", HttpStatus.OK, users);
        }
        return ResponseHandler.generateResponse("User not avilable", HttpStatus.OK, null);
    }

    public ResponseEntity<Object> getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return ResponseHandler.generateResponse("Successfully Fetch user ", HttpStatus.OK, user);
        }
        return ResponseHandler.generateResponse("User on the requested id not found ", HttpStatus.OK, null);
    }

    public ResponseEntity<Object> getUserByUserEmail(String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()){
            return ResponseHandler.generateResponse("User with email " + email + " not found!!", HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse("", HttpStatus.OK, user);
    }
    public ResponseEntity<Object> getUserByType(String type){
        List<User> users = userRepository.findUserByType(type);
        if (!users.isEmpty()){
            return ResponseHandler.generateResponse("Successfully Fetch All "+type+" Users", HttpStatus.OK, users);
        }
        return ResponseHandler.generateResponse("User type "+type+" not avilable ", HttpStatus.OK, null);
    }

    public ResponseEntity<Object> createUser(User user){
        try {
            Optional<User> userPosted = findByEmail(user.getEmail());
            if (userPosted.isPresent()) {
                return ResponseHandler.generateResponse(" This user is already registered!!!", HttpStatus.FOUND, null);
            }
            //for jobseekers attach job category
            if (user.getType().equalsIgnoreCase("jobseeker") &&
                !user.getJobCategoryList().isEmpty()){
                   user.getJobCategoryList().forEach((categoryId)->{
                   Optional<JobCategory> jobCategory = jobCategoryRepository.findById(categoryId);
                   jobCategory.ifPresent(category->user.getJobCategory().add(jobCategory.get()));
                   });
                }
            user.setCreated_at(LocalDateTime.now());
            User status = userRepository.save(user);
            return ResponseHandler.generateResponse("user is registered successfully " , HttpStatus.OK, status);
        }catch (Exception ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

}
