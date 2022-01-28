package com.example.passport_service.service;

import com.example.passport_service.StubBuilder;
import com.example.passport_service.domain.Passport;
import com.example.passport_service.store.PassportRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class PassportServiceImplTest implements WithAssertions {

    @MockBean
    private PassportRepository repository;

    @Autowired
    private PassportService<Passport> service;

    @BeforeEach
    void setUp() {
        when(repository.existsById(any())).thenReturn(true);
    }

    @Test
    void save() {
        final Passport expectedPassport = StubBuilder.builder()
            .id(1L)
            .build();
        when(repository.save(any())).thenReturn(expectedPassport);

        final Long result = service.save(new Passport());

        assertThat(result).isNotNull()
            .isEqualTo(expectedPassport.getId());
    }

    @Test
    void update() {
        when(repository.save(any())).thenReturn(new Passport());

        assertThat(service.update(new Passport())).isNotNull()
            .isTrue();
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(any());

        assertThat(service.delete(1L)).isNotNull()
            .isTrue();
    }

    @Test
    void findAll() {
        final Passport first = StubBuilder.builder()
            .id(1L)
            .build();
        final Passport second = StubBuilder.builder()
            .id(2L)
            .build();
        when(repository.findAll()).thenReturn(List.of(first, second));

        assertThat(service.findAll()).isNotNull()
            .hasSize(2)
            .contains(first, second);
    }

    @Test
    void findBySerial() {
        final Long serial = 1210L;
        final Passport first = StubBuilder.builder()
            .serial(serial)
            .build();
        final Passport second = StubBuilder.builder()
            .serial(serial)
            .build();
        when(repository.findPassportsBySerial(any())).thenReturn(List.of(first, second));

        assertThat(service.findBySerial(serial)).isNotNull()
            .hasSize(2)
            .contains(first, second)
            .allMatch(passport -> passport.getSerial().equals(serial));
    }

    @Test
    void findUnavailable() {
        final Passport first = StubBuilder.builder()
            .expiredDate(LocalDate.now().minusMonths(2))
            .build();
        final Passport second = StubBuilder.builder()
            .expiredDate(LocalDate.now().minusMonths(3))
            .build();
        when(repository.findPassportsWithExpiredDate()).thenReturn(List.of(first, second));

        assertThat(service.findUnavailable()).isNotNull()
            .hasSize(2)
            .contains(first, second);
    }

    @Test
    void findReplaceable() {
        final Passport first = StubBuilder.builder()
            .expiredDate(LocalDate.now().plusMonths(2))
            .build();
        final Passport second = StubBuilder.builder()
            .expiredDate(LocalDate.now().plusMonths(1))
            .build();
        when(repository.findReplaceablePassports()).thenReturn(List.of(first, second));

        assertThat(service.findReplaceable()).isNotNull()
            .hasSize(2)
            .contains(first, second);
    }
}