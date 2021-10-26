package com.livi.demo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service_health_status")
//@Table(name = "service_health_status")
public class HealthStatus implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "health_status_id", nullable = false)
  protected long healthStatusId;
  @NonNull
  @Column(name = "service_name", nullable = false)
  protected String serviceName;
  @NonNull
  @Column(name = "service_url", nullable = false, unique = true)
  protected String serviceUrl;
  @NonNull
  @Column(name = "status", columnDefinition = "varchar(20) default 'Not yet Added'")
  protected String status;
  @NonNull
  @CreatedDate
  @Column(name = "updated_date", nullable = false)
  protected Date updatedDate;

}
