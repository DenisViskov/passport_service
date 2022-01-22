-- Drop table passport
drop table if exists passport;


-- Create table passport
create table passport
(
    id                bigserial    not null,
    serial            int8         not null,
    number            int8         not null,
    name              varchar(30)  not null,
    surname           varchar(30)  not null,
    birth_date        date         not null,
    issuing_authority varchar(255) not null,
    date_of_issue     date         not null,
    expired_date      date         not null,

    -- constraints
    constraint PASSPORT_ID_PK primary key (id),
    constraint PASSPORT_NUMBER_UNIQUE unique (number),
    constraint PASSPORT_CHECK_BIRTH_DATE check (birth_date > TO_DATE('01.01.1920', 'dd.mm.yyyy'))
);


-- Create indexes on id, serial
create unique index PASSPORT_ID_UNIQUE_INDEX on passport (id);
create index PASSPORT_SERIAL_INDEX on passport (serial);
