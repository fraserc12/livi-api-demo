package com.livi.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceUrlChangeRequest {
  @NotNull
  @JsonProperty("url")
  private String serviceUrl;
  @JsonProperty("newUrl")
  private String newServiceUrl;
}
