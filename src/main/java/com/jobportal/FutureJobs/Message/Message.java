package com.jobportal.FutureJobs.Message;

import com.jobportal.FutureJobs.User.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

             @ManyToOne(cascade = CascadeType.ALL)
             @JoinColumn(name = "from_user")
            private User fromUser;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_user")
     private User toUser;
            @Valid
            @Column(name ="subject")
            private String subject;
            @Valid
            @NotNull
            @Column(name ="body")
            private String body;
            @Valid
            @Column(name ="status")
            private String status;

    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "modified_at")
    private LocalDateTime modified_at;


    public Message() {
    }

    public Message(Long id, User fromUser, User toUser, String subject, String body, String status) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.subject = subject;
        this.body = body;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }
}
