package com.jobportal.FutureJobs.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query
    Optional<User> findUserByEmail(String email);

    @Query
    List<User> findUserByType(String type);

    @Query()
    Optional<User> findUserByNameAndType(String name, String type);

}
