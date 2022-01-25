package com.example.passport_service.service;

import com.example.passport_service.domain.Passport;
import com.example.passport_service.store.PassportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PassportServiceImpl implements PassportService<Passport> {

    @Autowired
    private PassportRepository repository;

    @Override public Long save(final Passport passport) {
        log.info("call save {}", passport);

        final Passport result = repository.save(passport);

        return result.getId();
    }

    @Override public boolean update(final Passport passport) {
        log.info("call update {}", passport);

        if (!existsById(passport.getId())) {
            return false;
        }

        save(passport);
        return true;
    }

    @Override public boolean delete(final Long id) {
        log.info("call delete by id: {}", id);

        if (!existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    @Override public List<Passport> findAll() {
        log.info("call findAll");

        return repository.findAll();
    }

    @Override public List<Passport> findBySerial(final Long serial) {
        log.info("call find by serial: {}", serial);

        return repository.findPassportsBySerial(serial);
    }

    @Override public List<Passport> findUnavailable() {
        log.info("call find unavailable");

        return repository.findPassportsWithExpiredDate();
    }

    @Override public List<Passport> findReplaceable() {
        log.info("call find replaceable");

        return repository.findReplaceablePassports();
    }

    private boolean existsById(final Long id) {
        if (!repository.existsById(id)) {
            log.info("Passport with given id does not exists, id: {}", id);
            return false;
        }
        return true;
    }
}
