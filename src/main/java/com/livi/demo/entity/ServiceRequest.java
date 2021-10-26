package com.livi.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceRequest {
  @NotNull
  private String url;
  @NotNull
  @JsonProperty("name")
  private String serviceName;
  private String status;
}
