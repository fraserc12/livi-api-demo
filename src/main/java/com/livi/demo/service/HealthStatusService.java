package com.livi.demo.service;

import com.livi.demo.domain.HealthStatus;
import com.livi.demo.entity.HealthStatusResponse;
import com.livi.demo.entity.ServiceRequest;
import com.livi.demo.repository.HealthStatusRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class HealthStatusService {

  private static final String APPLICATION_UP = "OK";
  private static final String APPLICATION_DOWN = "FAIL";
  private final HealthStatusRepository healthStatusRepository;
  private RestTemplate restTemplate = new RestTemplate();

  public HealthStatusService(HealthStatusRepository healthStatusRepository) {
    this.healthStatusRepository = healthStatusRepository;
  }

  public HealthStatus createNewService(ServiceRequest serviceRequest) {
    HealthStatus healthStatus = new HealthStatus();
    healthStatus.setServiceName(serviceRequest.getServiceName());
    healthStatus.setServiceUrl(serviceRequest.getUrl());
    healthStatus.setUpdatedDate(new Date());
    healthStatusRepository.save(healthStatus);
    return healthStatus;
  }

  /* using CompletableFuture fetch URL using GET and store OK or FAIL */
  void fetchServiceUrlCheck(List<HealthStatus> services) throws ExecutionException, InterruptedException {
    List<CompletableFuture<String>> allFutures = new ArrayList<>();
    services.forEach(this::fetchUrl);
    CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).get();
  }

  @Async
  public CompletableFuture<HealthStatus> fetchUrl(HealthStatus status) {
    try {
      restTemplate.getForEntity(status.getServiceUrl(), String.class);
        status.setStatus(APPLICATION_UP);
        status.setUpdatedDate(new Date());
        HealthStatus saved = healthStatusRepository.save(status);
      return CompletableFuture.completedFuture(saved);
    } catch (Exception ex) {
      status.setStatus(APPLICATION_DOWN);
      status.setUpdatedDate(new Date());
      HealthStatus saved = healthStatusRepository.save(status);
      return CompletableFuture.completedFuture(saved);
    }
  }

  public List<HealthStatus> getAllServices() {
    return healthStatusRepository.findAll();
  }

  public void deleteService(String serviceUrl) {
    Optional<HealthStatus> status = healthStatusRepository.findByServiceUrl(serviceUrl);
    if(status.isPresent()) {
      healthStatusRepository.deleteByServiceUrl(serviceUrl);
    } else {
      throw new EntityNotFoundException();
    }
  }

  public Optional<HealthStatus> updateService(ServiceRequest serviceRequest) {
    //TODO: what if not found?
    Optional<HealthStatus> status = healthStatusRepository.findByServiceUrl(serviceRequest.getUrl());
    status.ifPresent(
        p -> {
          p.setServiceUrl(serviceRequest.getUrl());
          p.setServiceName(serviceRequest.getServiceName());
          p.setUpdatedDate(new Date());
        }
    );

    return status;
  }

  public HealthStatusResponse mapToHealthStatusResponse(HealthStatus status) {
    //TODO: add moodelmapper?
    return new HealthStatusResponse(
        status.getStatus(), status.getServiceName(), status.getUpdatedDate(), status.getServiceUrl());
  }

  public List<HealthStatusResponse> mapListToHealthStatusResponse(List<HealthStatus> all) {
    //TODO: add moodelmapper?
    List<HealthStatusResponse> allRes = new ArrayList<>();
    all.forEach(
        s -> allRes.add(new HealthStatusResponse(s.getStatus(), s.getServiceName(), s.getUpdatedDate(), s.getServiceUrl()))
    );
    return allRes;
  }

  public Optional<HealthStatus> getServiceByUrl(String url) {
    return this.healthStatusRepository.findByServiceUrl(url);
  }

  public HealthStatus saveStatus(HealthStatus status) {
    return this.healthStatusRepository.save(status);
  }
}
