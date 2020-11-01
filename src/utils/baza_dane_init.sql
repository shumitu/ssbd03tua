--------------------------------------------------------------------------------
--                         Initialization records                             --
--------------------------------------------------------------------------------

--
-- Table: public.account
--
INSERT INTO account (password, email, motto, confirmed, active, password_change_required)
VALUES ('ad945840f970570824a1b337f096125671e687b50804b098d812de814d275af5',
        'admin@ssbd03.com',
        'W naszym zakładzie pracy nie ma etatu statysty',
        true,
        true,
        false);
INSERT INTO account (password, email, motto, confirmed, active, password_change_required)
VALUES ('ad945840f970570824a1b337f096125671e687b50804b098d812de814d275af5',
        'employee@ssbd03.com',
        'Z pracy ludzkich rąk rodzi się zawsze dobro narodu',
        true,
        true,
        false);
INSERT INTO account (password, email, motto, confirmed, active, password_change_required)
VALUES ('ad945840f970570824a1b337f096125671e687b50804b098d812de814d275af5',
        'candidate@ssbd03.com',
        'Pracując dla kraju, pracujesz dla siebie',
        true,
        true,
        false);
INSERT INTO account (password, email, motto, confirmed, active, password_change_required)
VALUES ('ad945840f970570824a1b337f096125671e687b50804b098d812de814d275af5',
        'admin.employee@ssbd03.com',
        'Jedność pracy jednością narodu',
        true,
        true,
        false);
INSERT INTO account (password, email, motto, confirmed, active, password_change_required)
VALUES ('ad945840f970570824a1b337f096125671e687b50804b098d812de814d275af5',
        'admin.candidate@ssbd03.com',
        'Miarą patriotyzmu jest rzetelna praca!',
        false,
        false,
        false);
INSERT INTO account (password, email, motto, confirmed, active, password_change_required)
VALUES ('ad945840f970570824a1b337f096125671e687b50804b098d812de814d275af5',
        'employee.candidate@ssbd03.com',
        'Nasze młodzieżowe zawołanie. Zrobić więcej niż nakazuje obowiązek!',
        true,
        false,
        false);
INSERT INTO account (password, email, motto, confirmed, active, password_change_required)
VALUES ('ad945840f970570824a1b337f096125671e687b50804b098d812de814d275af5',
        'admin.employee.candidate@ssbd03.com',
        'Wielki zasiew, bujne plony, radość ludu',
        true,
        true,
        false);



--
-- Table: public.access_level
--
INSERT INTO access_level (account_id, level, active)
VALUES (1, 'ADMIN', true);
INSERT INTO access_level (account_id, level, active)
VALUES (2, 'EMPLOYEE', true);
INSERT INTO access_level (account_id, level, active)
VALUES (3, 'CANDIDATE', true);
INSERT INTO access_level (account_id, level, active)
VALUES (4, 'ADMIN', true);
INSERT INTO access_level (account_id, level, active)
VALUES (4, 'EMPLOYEE', false);
INSERT INTO access_level (account_id, level, active)
VALUES (5, 'ADMIN', false);
INSERT INTO access_level (account_id, level, active)
VALUES (5, 'CANDIDATE', true);
INSERT INTO access_level (account_id, level, active)
VALUES (6, 'EMPLOYEE', true);
INSERT INTO access_level (account_id, level, active)
VALUES (6, 'CANDIDATE', false);
INSERT INTO access_level (account_id, level, active)
VALUES (7, 'ADMIN', true);
INSERT INTO access_level (account_id, level, active)
VALUES (7, 'EMPLOYEE', true);
INSERT INTO access_level (account_id, level, active)
VALUES (7, 'CANDIDATE', true);



--
-- Table: public.admin
--
INSERT INTO admin (id) VALUES (1);
INSERT INTO admin (id) VALUES (4);
INSERT INTO admin (id) VALUES (6);
INSERT INTO admin (id) VALUES (10);

--
-- Table: public.employee
--
INSERT INTO employee (id) VALUES (2);
INSERT INTO employee (id) VALUES (5);
INSERT INTO employee (id) VALUES (8);
INSERT INTO employee (id) VALUES (11);

--
-- Table: public.candidate
--
INSERT INTO candidate (id, first_name, last_name)
VALUES (3, 'John', 'Smith');
INSERT INTO candidate (id, first_name, last_name)
VALUES (7, 'Jan', 'Kowalski');
INSERT INTO candidate (id, first_name, last_name)
VALUES (9, 'Johan', 'Schmidt');
INSERT INTO candidate (id, first_name, last_name)
VALUES (12, 'Janos', 'Kovacs');



--
-- Table: public.login_info
--
INSERT INTO login_info (account_id) VALUES (1);
INSERT INTO login_info (account_id) VALUES (2);
INSERT INTO login_info (account_id) VALUES (3);
INSERT INTO login_info (account_id) VALUES (4);
INSERT INTO login_info (account_id) VALUES (5);
INSERT INTO login_info (account_id) VALUES (6);
INSERT INTO login_info (account_id) VALUES (7);



--
-- Table: public.starship
--
INSERT INTO starship (name, crew_capacity, maximum_weight, fuel_capacity,
                      maximum_speed, year_of_manufacture)
VALUES ('Nostromo', 125, 13000, 10000000, 453286195.2, 2018);



--
-- Table: public.offer
--
INSERT INTO offer (starship_id, price, flight_start_time, flight_end_time,
                   destination, description, total_cost, total_weight)
VALUES (1, 75029375, current_timestamp+'10:35', current_timestamp+'312:13',
        'Odległe zakątki Marsa skrywające odwieczne tajemnice budowniczych piramid.',
        'Niezapomniana podróż w bezkresną otchłań kosmosu. Niepowtarzalna okazja
        do uczestnictwa w pierwszej turystycznej misji kosmicznej.',
        47507293075, 81.4);
INSERT INTO offer (starship_id, price, flight_start_time, flight_end_time,
                   destination, description, total_cost, total_weight, hidden, open)
VALUES (1, 75029375, current_timestamp+'610:35', current_timestamp+'912:13',
        'Venus',
        'W sumie zwykła wycieczka.',
        42137293075, 0, false, true);
INSERT INTO offer (starship_id, price, flight_start_time, flight_end_time,
                   destination, description, total_cost, total_weight, hidden, open)
VALUES (1, 705029375, current_timestamp+'1010:35', current_timestamp+'1302:13',
        'Pluton',
        'Niezwykła zwykła wycieczka.',
        402137293075, 0, false, false);



--
-- Table: public.application
--
INSERT INTO application (candidate_id, offer_id, weight, examination_code,
                         motivational_letter, created_time)
VALUES (3, 1, 81.4, '134bbas52363n', 'I really, REALLY want it.', current_timestamp);



--
-- Table: public.category
--
INSERT INTO category (name) VALUES ('Accepted');
INSERT INTO category (name) VALUES ('Reserve');
INSERT INTO category (name) VALUES ('Rejected');



--
-- Table: public.review
--
INSERT INTO review (reviewer_id, application_id, category_id) VALUES (2, 1, 1);
