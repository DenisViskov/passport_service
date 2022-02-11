package com.example.passport_service.service;

import com.example.passport_service.domain.Passport;
import com.example.passport_service.dto.PassportDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapperService {
    Passport toEntity(final PassportDto passportDto);

    PassportDto toDto(final Passport passport);
}
