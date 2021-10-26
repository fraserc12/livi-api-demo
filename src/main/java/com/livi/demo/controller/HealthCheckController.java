package com.livi.demo.controller;

import com.livi.demo.domain.HealthStatus;
import com.livi.demo.entity.HealthStatusResponse;
import com.livi.demo.entity.ServiceRequest;
import com.livi.demo.entity.ServiceUrlChangeRequest;
import com.livi.demo.service.HealthStatusService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/health")
public class HealthCheckController {

  private final HealthStatusService healthStatusService;
  private UrlValidator defaultValidator = new UrlValidator();

  public HealthCheckController(
      HealthStatusService healthStatusService) {
    this.healthStatusService = healthStatusService;
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/all")
  public List<HealthStatusResponse> getAllServices() {
    return healthStatusService.mapListToHealthStatusResponse(healthStatusService.getAllServices());
  }

  @PutMapping("/create")
  public ResponseEntity addService(@Valid @RequestBody ServiceRequest serviceRequest) {
    UrlValidator defaultValidator = new UrlValidator();
    if(!defaultValidator.isValid(serviceRequest.getUrl())) {
      return new ResponseEntity<>(
          String.format("Invalid URL [%s]",serviceRequest.getUrl()),
          HttpStatus.BAD_REQUEST);
    }

    try {
      return ResponseEntity.ok(healthStatusService.mapToHealthStatusResponse(
          healthStatusService.createNewService(serviceRequest)
      ));
    } catch(DataIntegrityViolationException ex) {
      return new ResponseEntity<>(
          String.format("Service with URL [%s] Already exists",serviceRequest.getUrl()),
          HttpStatus.CONFLICT);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteService(@Valid @RequestParam String serviceUrl) {
    try {
      healthStatusService.deleteService(serviceUrl);
      return ResponseEntity.ok().build();
    } catch(EntityNotFoundException ex) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/update")
  public ResponseEntity updateService(@Valid @RequestBody ServiceRequest serviceRequest) {
    if(!defaultValidator.isValid(serviceRequest.getUrl())) {
      return new ResponseEntity<>(
          String.format("Invalid URL [%s]",serviceRequest.getUrl()),
          HttpStatus.BAD_REQUEST);
    }

    Optional<HealthStatus> updatedStatus = healthStatusService.updateService(serviceRequest);
    if(updatedStatus.isPresent()) {
      return ResponseEntity.ok(healthStatusService.mapToHealthStatusResponse(updatedStatus.get()));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/update/url")
  public ResponseEntity updateServiceUrl(@Valid @RequestBody ServiceUrlChangeRequest request) {
    String newUrl = request.getNewServiceUrl();
    if(!defaultValidator.isValid(newUrl)) {
      return new ResponseEntity<>(
          String.format("Invalid URL [%s]",newUrl),
          HttpStatus.BAD_REQUEST);
    }

    Optional<HealthStatus> found = healthStatusService.getServiceByUrl(request.getServiceUrl());
    if(found.isPresent()) {
      found.get().setServiceUrl(newUrl);
      return ResponseEntity.ok(
          healthStatusService.saveStatus(found.get())
      );
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
