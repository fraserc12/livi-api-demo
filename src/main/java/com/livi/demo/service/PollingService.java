package com.livi.demo.service;

import java.util.concurrent.ExecutionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class PollingService {

  private final HealthStatusService statusService;

  public PollingService(HealthStatusService statusService) {
    this.statusService = statusService;
  }

  @Async
  @Scheduled(fixedRate = 5000, initialDelay = 100)
  public void scheduleFixedRateTask() throws ExecutionException, InterruptedException {
    statusService.fetchServiceUrlCheck(statusService.getAllServices());
  }
}
