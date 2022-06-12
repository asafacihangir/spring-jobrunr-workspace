package com.phoenix.jobrunr.service.base;

public interface SampleService {

  void doLongRunningTask(String name) throws InterruptedException;
}
