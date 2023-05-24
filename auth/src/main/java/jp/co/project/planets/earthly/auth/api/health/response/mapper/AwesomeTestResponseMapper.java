package jp.co.project.planets.earthly.auth.api.health.response.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import jp.co.project.planets.earthly.auth.api.health.response.AwesomeTestResponse;
import jp.co.project.planets.earthly.auth.model.dto.health.HealthResultDto;

@Mapper
public interface AwesomeTestResponseMapper {

    AwesomeTestResponseMapper INSTANCE = Mappers.getMapper(AwesomeTestResponseMapper.class);

    AwesomeTestResponse convertToResponse(HealthResultDto healthResultDto);
}
