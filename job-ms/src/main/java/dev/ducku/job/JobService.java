package dev.ducku.job;

import dev.ducku.job.response.JobResponse;

import java.util.List;

public interface JobService {
    List<Job> findAll();

    void createJob(Job job);

    JobResponse findJob(Long id);

    Job deleteJobById(Long id);

    Job updateJobById(Long id, Job job);
}
