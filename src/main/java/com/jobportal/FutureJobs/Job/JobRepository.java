package com.jobportal.FutureJobs.Job;

import com.jobportal.FutureJobs.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = "select * from job j where j.vacancy_id =?1", nativeQuery = true)
    Optional<Job> findJobByVacancyId(String vacancy_id);

}
