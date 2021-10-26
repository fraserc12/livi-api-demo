//package com.livi.demo.mapper;
//
//
//import com.livi.demo.domain.HealthStatus;
//import com.livi.demo.entity.HealthStatusResponse;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface HealthStatusMapper {
//
//  HealthStatusMapper INSTANCE = Mappers.getMapper( HealthStatusMapper.class );
//
//  @Mapping(source = "res.serviceName", target = "serviceName")
//  @Mapping(source = "res.serviceUrl", target = "url")
//  @Mapping(source = "res.updatedDate", target = "updatedDate")
//  @Mapping(source = "res.status", target = "status")
//  HealthStatusResponse healthStatusToHealthStatusDto(@MappingTarget HealthStatusResponse res, HealthStatus healthStatus);
//}
//
//
