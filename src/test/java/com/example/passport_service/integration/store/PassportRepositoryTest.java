package com.example.passport_service.integration.store;

import com.example.passport_service.domain.Passport;
import com.example.passport_service.store.PassportRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PassportRepositoryTest implements WithAssertions {

    @Autowired
    private PassportRepository repository;

    private static final Passport STUB = new Passport();

    @BeforeAll
    static void beforeAll() {
        STUB.setSerial(4561L);
        STUB.setNumber(123456L);
        STUB.setName("Andrew");
        STUB.setSurname("Alf");
        STUB.setBirthDate(toDate(LocalDate.of(1990, 03, 05)));
        STUB.setIssuingAuthority("Test");
        STUB.setDateOfIssue(toDate(LocalDate.of(2010, 03, 05)));
        STUB.setExpiredDate(toDate(LocalDate.of(2022, 03, 05)));
    }

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
        final Passport saved = repository.save(STUB);
        STUB.setId(saved.getId());

        assertThat(saved).isNotNull()
            .isEqualTo(STUB);

        repository.delete(saved);
        STUB.setId(null);
    }

    @Test
    void updatePassport() {
        final Passport saved = repository.save(STUB);
        final String expectedSurname = "updatedSurname";
        saved.setSurname(expectedSurname);

        final Passport updated = repository.save(saved);

        assertThat(updated.getSurname()).isNotNull()
            .isEqualTo(expectedSurname);

        repository.delete(saved);
    }

    @Test
    void deletePassport() {
        final Passport saved = repository.save(STUB);
        repository.delete(saved);

        final Passport expected = repository.findById(saved.getId())
            .orElse(null);

        assertThat(expected).isNull();
    }

    @Test
    void passportCheckBirthDateConstraints() {
        final Date stubBirthDate = STUB.getBirthDate();
        STUB.setBirthDate(toDate(LocalDate.of(1915, 02, 15)));

        final String expectedMessage = "could not execute statement; SQL [n/a];";
        final DataIntegrityViolationException dataIntegrityViolationException = assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.save(STUB)
        );

        assertThat(dataIntegrityViolationException).isNotNull()
            .hasMessageContaining(expectedMessage);

        STUB.setBirthDate(stubBirthDate);
    }

    private static Date toDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }
}