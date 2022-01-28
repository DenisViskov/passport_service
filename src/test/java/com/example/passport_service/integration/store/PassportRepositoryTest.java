package com.example.passport_service.integration.store;

import com.example.passport_service.StubBuilder;
import com.example.passport_service.domain.Passport;
import com.example.passport_service.store.PassportRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PassportRepositoryTest implements WithAssertions {

    @Autowired
    private PassportRepository repository;

    @Test
    void findPassportsBySerial() {
        final Long expectedSerial = 4315L;

        final List<Passport> passportsBySerial = repository.findPassportsBySerial(expectedSerial);

        assertThat(passportsBySerial).isNotNull()
            .hasSize(4)
            .allMatch(passport -> passport.getSerial().equals(expectedSerial));
    }

    @Test
    void findPassportsWithExpiredDate() {
        final List<Passport> passportsWithExpiredDate = repository.findPassportsWithExpiredDate();

        assertThat(passportsWithExpiredDate).isNotNull()
            .hasSize(2)
            .allMatch(passport -> passport.getExpiredDate().before(new Date()));

    }

    @Test
    void findReplaceablePassports() {
        final List<Passport> replaceablePassports = repository.findReplaceablePassports();

        assertThat(replaceablePassports).isNotNull()
            .hasSize(2);

        replaceablePassports.forEach(passport -> {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(passport.getExpiredDate());
            calendar.add(Calendar.MONTH, -3);
            final Date minusThreeMonth = calendar.getTime();

            assertThat(new Date()).isBetween(minusThreeMonth, passport.getExpiredDate());
        });
    }

    @Test
    void createPassport() {
        final Passport passport = StubBuilder.builder().build();
        final Passport saved = repository.save(passport);
        passport.setId(saved.getId());

        assertThat(saved).isNotNull()
            .isEqualTo(passport);

        repository.delete(saved);
    }

    @Test
    void updatePassport() {
        final Passport saved = repository.save(StubBuilder.builder().build());
        final String expectedSurname = "updatedSurname";
        saved.setSurname(expectedSurname);

        final Passport updated = repository.save(saved);

        assertThat(updated.getSurname()).isNotNull()
            .isEqualTo(expectedSurname);

        repository.delete(saved);
    }

    @Test
    void deletePassport() {
        final Passport saved = repository.save(StubBuilder.builder().build());
        repository.delete(saved);

        final Passport expected = repository.findById(saved.getId())
            .orElse(null);

        assertThat(expected).isNull();
    }

    @Test
    void passportCheckBirthDateConstraints() {
        final Passport passport = StubBuilder.builder()
            .birthDate(LocalDate.of(1915, 02, 15))
            .build();

        final String expectedMessage = "could not execute statement; SQL [n/a];";
        final DataIntegrityViolationException dataIntegrityViolationException = assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.save(passport)
        );

        assertThat(dataIntegrityViolationException).isNotNull()
            .hasMessageContaining(expectedMessage);
    }
}