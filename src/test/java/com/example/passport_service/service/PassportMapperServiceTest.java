package com.example.passport_service.service;

import com.example.passport_service.StubBuilder;
import com.example.passport_service.domain.Passport;
import com.example.passport_service.dto.PassportDto;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PassportMapperServiceTest implements WithAssertions {

    @Autowired
    private PassportMapperService service;

    @Test
    void toEntity() {
        final Passport passport = StubBuilder.builder().build();
        final PassportDto passportDto = service.toDto(passport);

        assertBoth(passport, passportDto);
    }

    @Test
    void toDto() {
        final PassportDto passportDto = PassportDto.builder()
            .id(1L)
            .name("Test")
            .surname("test2")
            .number(123456L)
            .serial(1234L)
            .build();
        final Passport passport = service.toEntity(passportDto);

        assertBoth(passport, passportDto);
    }

    private void assertBoth(final Passport passport, final PassportDto passportDto) {
        assertThat(passport.getSerial()).isEqualTo(passportDto.getSerial());
        assertThat(passport.getSurname()).isEqualTo(passportDto.getSurname());
        assertThat(passport.getName()).isEqualTo(passportDto.getName());
        assertThat(passport.getExpiredDate()).isEqualTo(passportDto.getExpiredDate());
        assertThat(passport.getBirthDate()).isEqualTo(passportDto.getBirthDate());
        assertThat(passport.getDateOfIssue()).isEqualTo(passportDto.getDateOfIssue());
        assertThat(passport.getId()).isEqualTo(passportDto.getId());
        assertThat(passport.getNumber()).isEqualTo(passportDto.getNumber());
        assertThat(passport.getIssuingAuthority()).isEqualTo(passportDto.getIssuingAuthority());
    }
}