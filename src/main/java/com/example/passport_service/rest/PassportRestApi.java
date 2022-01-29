package com.example.passport_service.rest;

import com.example.passport_service.domain.Passport;
import com.example.passport_service.dto.PassportDto;
import com.example.passport_service.service.PassportMapperService;
import com.example.passport_service.service.PassportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/passports")
public class PassportRestApi {

    private final PassportService<Passport> passportService;

    private final PassportMapperService mapperService;

    @PostMapping(value = "/save")
    public ResponseEntity<Long> save(@Valid @RequestBody final PassportDto passportDto) {
        final Passport passport = mapperService.toEntity(passportDto);
        final Long id = passportService.save(passport);
        return ResponseEntity.ok(id);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(
        @RequestParam final Long id,
        @Valid @RequestBody final PassportDto passportDto
    ) {
        final Passport passport = mapperService.toEntity(passportDto);
        passport.setId(id);
        final boolean result = passportService.update(passport);
        return result ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam final Long id) {
        final boolean result = passportService.delete(id);
        return result ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @GetMapping(value = "/find")
    public ResponseEntity<List<PassportDto>> find() {
        return ResponseEntity.ok(
            passportService.findAll()
                .stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/find", params = "serial")
    public ResponseEntity<List<PassportDto>> findBySerial(@RequestParam final Long serial) {
        return ResponseEntity.ok(
            passportService.findBySerial(serial)
                .stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/unavailable")
    public ResponseEntity<List<PassportDto>> unavailable() {
        return ResponseEntity.ok(
            passportService.findUnavailable()
                .stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/find-replaceable")
    public ResponseEntity<List<PassportDto>> findReplaceable() {
        return ResponseEntity.ok(
            passportService.findReplaceable()
                .stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList())
        );
    }
}
