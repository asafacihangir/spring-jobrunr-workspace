package com.phoenix.jobrunr.web;

import com.phoenix.jobrunr.service.base.SampleService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SimpleSampleController {

  private final SampleService sampleService;
  private final JobScheduler jobScheduler;


  public SimpleSampleController(SampleService sampleService, JobScheduler jobScheduler) {
    this.sampleService = sampleService;
    this.jobScheduler = jobScheduler;
  }

  @GetMapping("/run-job")
  public String enqueueOneJob(@RequestParam(name = "name", defaultValue = "world") String name) {
    jobScheduler.enqueue(() -> sampleService.doLongRunningTask(name));
    return "One Job Enqueued";
  }


}
