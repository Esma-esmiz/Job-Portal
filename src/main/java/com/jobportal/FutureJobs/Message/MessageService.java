package com.jobportal.FutureJobs.Message;

import com.jobportal.FutureJobs.Application.Application;
import com.jobportal.FutureJobs.Job.Job;
import com.jobportal.FutureJobs.Response.ResponseHandler;
import com.jobportal.FutureJobs.User.User;
import com.jobportal.FutureJobs.User.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ResponseEntity<Object> getMessages() {
        List<Message> messages = messageRepository.findAll();
        if (!messages.isEmpty()) {
            return ResponseHandler.generateResponse("Successfully Fetch All messages ", HttpStatus.OK, messages);
        }
        return ResponseHandler.generateResponse("message not available", HttpStatus.OK, null);

    }

    public ResponseEntity<Object> getMessageById(Long messageId) {

        Optional<Message> messages = messageRepository.findById(messageId);
        if (messages.isPresent()) {
            return ResponseHandler.generateResponse("Successfully Fetch message ", HttpStatus.OK, messages);
        }
        return ResponseHandler.generateResponse("message not found", HttpStatus.OK, null);

    }

    // not tested
    public ResponseEntity<Object> getMessageBox(Long sender, Long receiver) {
        Optional<User> sender1 = userRepository.findById(sender);
        Optional<User> receiver1 = userRepository.findById(receiver);


        if (sender1.isEmpty() || receiver1.isEmpty()) {
            return ResponseHandler.generateResponse("user is not found", HttpStatus.NOT_FOUND, null);
        }
        List<List<Message>> box = new ArrayList<List<Message>>();
        box.add(sender1.get().getToUser());
        box.add(sender1.get().getFromUser());
        return ResponseHandler.generateResponse("message is registered successfully ", HttpStatus.OK, box);
    }

    public ResponseEntity<Object> createMessage(Long fromUserId, Long toUserId, @Valid Message message) {

        try {
            Optional<User> fromUser = userRepository.findById(fromUserId);
            Optional<User> toUser = userRepository.findById(toUserId);

            if (fromUser.isEmpty() || toUser.isEmpty()) {
                return ResponseHandler.generateResponse("user is not found", HttpStatus.NOT_FOUND, null);
            }
            message.setFromUser(fromUser.get());
            message.setToUser(toUser.get());
            message.setStatus("sent"); // sent delivered read
            message.setCreated_at(LocalDateTime.now());
            Message status = messageRepository.save(message);
            return ResponseHandler.generateResponse("message is registered successfully ", HttpStatus.OK, status);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
