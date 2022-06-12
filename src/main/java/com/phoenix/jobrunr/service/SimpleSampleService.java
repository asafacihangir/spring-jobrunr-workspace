package com.phoenix.jobrunr.service;

import com.phoenix.jobrunr.service.base.SampleService;
import org.springframework.stereotype.Service;

@Service
public class SimpleSampleService implements SampleService {



  @Override
  public void doLongRunningTask(String name) throws InterruptedException{
    System.out.println("Hello " + name + " from JobRunr");
    Thread.sleep(8_500);
  }

}
