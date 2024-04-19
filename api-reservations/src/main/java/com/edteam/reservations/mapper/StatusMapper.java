package com.edteam.reservations.mapper;

import com.edteam.reservations.dto.StatusDTO;
import com.edteam.reservations.model.Status;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface StatusMapper extends Converter<Status, StatusDTO> {

    @Override
    StatusDTO convert(Status source);

}