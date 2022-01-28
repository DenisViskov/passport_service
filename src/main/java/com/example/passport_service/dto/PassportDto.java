package com.example.passport_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Data
@Builder
public class PassportDto {

    @JsonProperty(value = "id")
    private Long id;

    @Digits(integer = 4, fraction = 0, message = "only 4 digits allowed")
    @JsonProperty(value = "serial_number", required = true)
    private Long serial;

    @Digits(integer = 6, fraction = 0, message = "only 6 digits allowed")
    @JsonProperty(value = "main_number", required = true)
    private Long number;

    @NotBlank(message = "name must be not empty")
    @JsonProperty(value = "name", required = true)
    private String name;

    @NotBlank(message = "surname must be not empty")
    @JsonProperty(value = "surname", required = true)
    private String surname;

    @Past(message = "birth date must be in past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonProperty(value = "birth_date", required = true)
    private Date birthDate;

    @NotBlank(message = "issuing_authority must be not empty")
    @JsonProperty(value = "issuing_authority", required = true)
    private String issuingAuthority;

    @PastOrPresent(message = "date_of_issue must be past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonProperty(value = "date_of_issue", required = true)
    private Date dateOfIssue;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonProperty(value = "expired_date", required = true)
    private Date expiredDate;
}
