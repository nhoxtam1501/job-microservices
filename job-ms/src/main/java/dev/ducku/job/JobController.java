package dev.ducku.job;

import dev.ducku.job.response.JobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    private final JobService jobService;

    @Value("${message.default}")
    private String message;

    @Value("${server.port}")
    private String port;

    @GetMapping
    public ResponseEntity<List<Job>> findAll() {
        log.info("Processing on port: {}", port);
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Job job) {
        jobService.createJob(job);
        log.info("Processing on port: {}", port);
        return new ResponseEntity<>("Job added successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> findById(@PathVariable long id) {
        log.info("Processing on port: {}", port);
        return jobService.findJob(id) == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(jobService.findJob(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Job> deleteJobById(@PathVariable Long id) {
        return jobService.deleteJobById(id) == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(jobService.deleteJobById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJobById(@PathVariable long id, @RequestBody Job job) {
        return jobService.deleteJobById(id) == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(jobService.updateJobById(id, job), HttpStatus.OK);

    }

    @GetMapping("/message")
    public String getMessage() {
        log.info("Message from heaven: {}", message);
        return message;
    }


}
