package com.livi.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HealthStatusResponse {
  private String status;
  private String serviceName;
  @JsonFormat(pattern="dd-MM-yyyy HH:mm")
  private Date creationTime;
  private String url;
}
