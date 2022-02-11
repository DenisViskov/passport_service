package com.example.passport_service.store;

import com.example.passport_service.domain.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    List<Passport> findPassportsBySerial(final Long serial);

    @Query("select p from Passport p where p.expiredDate < current_date")
    List<Passport> findPassportsWithExpiredDate();

    @Query(
        value = "select * from Passport p where current_date between (p.expired_date - interval '3 month') and p.expired_date",
        nativeQuery = true
    )
    List<Passport> findReplaceablePassports();
}
