package com.livi.demo.repository;

import com.livi.demo.domain.HealthStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {

  Optional<HealthStatus> findByServiceUrl(String url);

  void deleteByServiceUrl(String serviceUrl);
}
