package com.jobportal.FutureJobs.SubEmployer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubEmployerRepository extends JpaRepository<SubEmployer, Long> {
//    @Query
//    Optional<SubEmployer> findSubEmployerBydepartment_name(String department_name);
    @Query
    Optional<SubEmployer> findSubEmployerByEmail(String email);
}
