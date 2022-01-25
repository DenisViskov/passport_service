package com.example.passport_service.service;

import com.example.passport_service.domain.Passport;
import com.example.passport_service.store.PassportRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
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
        final Passport expectedPassport = new Passport();
        expectedPassport.setId(1L);
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
        final Passport first = new Passport();
        final Passport second = new Passport();
        first.setId(1L);
        second.setId(2L);
        when(repository.findAll()).thenReturn(List.of(first, second));

        assertThat(service.findAll()).isNotNull()
            .hasSize(2)
            .contains(first, second);
    }

    @Test
    void findBySerial() {
        final Passport first = new Passport();
        final Passport second = new Passport();
        first.setSerial(1210L);
        second.setSerial(1210L);
        when(repository.findPassportsBySerial(any())).thenReturn(List.of(first, second));

        assertThat(service.findBySerial(1210L)).isNotNull()
            .hasSize(2)
            .contains(first, second)
            .allMatch(passport -> passport.getSerial().equals(1210L));
    }

    @Test
    void findUnavailable() {
        final Passport first = new Passport();
        final Passport second = new Passport();
        first.setExpiredDate(new Date());
        second.setExpiredDate(new Date());
        when(repository.findPassportsWithExpiredDate()).thenReturn(List.of(first, second));

        assertThat(service.findUnavailable()).isNotNull()
            .hasSize(2)
            .contains(first, second);
    }

    @Test
    void findReplaceable() {
        final Passport first = new Passport();
        final Passport second = new Passport();
        first.setExpiredDate(new Date());
        second.setExpiredDate(new Date());
        when(repository.findReplaceablePassports()).thenReturn(List.of(first, second));

        assertThat(service.findReplaceable()).isNotNull()
            .hasSize(2)
            .contains(first, second);
    }
}