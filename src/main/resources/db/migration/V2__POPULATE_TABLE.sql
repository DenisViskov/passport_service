-- Create fake passports data


insert into passport(serial, number, name, surname, birth_date, issuing_authority, date_of_issue, expired_date)
values (4210, 321456, 'John', 'Smith', to_date('02.12.1992', 'dd.mm.yyyy'),
        'National California Department №434 city of Los-Angeles', to_date('15.12.2010', 'dd.mm.yyyy'),
        current_date + interval '5 month');


insert into passport(serial, number, name, surname, birth_date, issuing_authority, date_of_issue, expired_date)
values (4211, 321759, 'Mark', 'Tven', to_date('22.11.1988', 'dd.mm.yyyy'),
        'National California Department №433 city of San-Diego', to_date('22.09.2012', 'dd.mm.yyyy'),
        current_date + interval '5 month');


insert into passport(serial, number, name, surname, birth_date, issuing_authority, date_of_issue, expired_date)
values (4211, 451759, 'Garret', 'Adams', to_date('05.07.1975', 'dd.mm.yyyy'),
        'National California Department №444 city of San-Francisco', to_date('10.02.2010', 'dd.mm.yyyy'),
        current_date + interval '5 month');


insert into passport(serial, number, name, surname, birth_date, issuing_authority, date_of_issue, expired_date)
values (4315, 456789, 'Rachel', 'Mayers', to_date('20.06.1977', 'dd.mm.yyyy'),
        'National California Department №566 city of Belmont', to_date('20.01.2010', 'dd.mm.yyyy'),
        to_date('20.01.2022', 'dd.mm.yyyy'));


insert into passport(serial, number, name, surname, birth_date, issuing_authority, date_of_issue, expired_date)
values (4315, 458799, 'Rudolf', 'Pitt', to_date('15.03.1966', 'dd.mm.yyyy'),
        'National California Department №566 city of Belmont', to_date('26.01.2008', 'dd.mm.yyyy'),
        to_date('10.01.2020', 'dd.mm.yyyy'));


insert into passport(serial, number, name, surname, birth_date, issuing_authority, date_of_issue, expired_date)
values (4315, 358729, 'Mila', 'Schmidt', to_date('15.04.1968', 'dd.mm.yyyy'),
        'National California Department №433 city of San-Diego', to_date('26.01.2008', 'dd.mm.yyyy'),
        current_date + interval '3 month');


insert into passport(serial, number, name, surname, birth_date, issuing_authority, date_of_issue, expired_date)
values (4315, 353734, 'Howard', 'Ford', to_date('15.04.1968', 'dd.mm.yyyy'),
        'National California Department №433 city of San-Diego', to_date('27.04.2009', 'dd.mm.yyyy'),
        current_date + interval '3 month');