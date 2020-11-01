--------------------------------------------------------------------------------
--                              Initialization                                --
--------------------------------------------------------------------------------

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

SET search_path = public, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = false;

--------------------------------------------------------------------------------
--                                 Cleaning                                   --
--------------------------------------------------------------------------------

DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS password_reset_token CASCADE;
DROP TABLE IF EXISTS access_level CASCADE;
DROP TABLE IF EXISTS admin CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS candidate CASCADE;
DROP TABLE IF EXISTS login_info CASCADE;
DROP TABLE IF EXISTS application CASCADE;
DROP TABLE IF EXISTS review CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS offer CASCADE;
DROP TABLE IF EXISTS starship CASCADE;
DROP VIEW IF EXISTS auth_view;

--------------------------------------------------------------------------------
--                                    MOK                                     --
--------------------------------------------------------------------------------

CREATE TABLE account (
     id                       bigserial                  NOT NULL,
     password                 char(64)                   NOT NULL,
     email                    varchar(254)               NOT NULL,
     motto                    varchar(128)               NOT NULL,
     confirmed                boolean      DEFAULT false NOT NULL,
     active                   boolean      DEFAULT true  NOT NULL,
     password_change_required boolean      DEFAULT true  NOT NULL,
     version                  bigint       DEFAULT 1     NOT NULL
);

CREATE TABLE password_reset_token (
      id              bigserial                                       NOT NULL,
      account_id      bigint                                          NOT NULL,
      token           char(64)                                        NOT NULL,
      expiration_time timestamp   DEFAULT current_timestamp + '00:15' NOT NULL,
      version         bigint      DEFAULT 1                           NOT NULL
);

CREATE TABLE access_level (
      id         bigserial            NOT NULL,
      account_id bigint               NOT NULL,
      level      varchar(16)          NOT NULL,
      active     boolean DEFAULT true NOT NULL,
      version    bigint  DEFAULT 1    NOT NULL
);

CREATE TABLE admin (
       id      bigint           NOT NULL,
       version bigint DEFAULT 1 NOT NULL
);

CREATE TABLE employee (
      id              bigint           NOT NULL,
      employee_number bigserial        NOT NULL,
      version         bigint DEFAULT 1 NOT NULL
);

CREATE TABLE candidate (
       id         bigint           NOT NULL,
       first_name varchar(16)      NOT NULL,
       last_name  varchar(32)      NOT NULL,
       version    bigint DEFAULT 1 NOT NULL
);

CREATE TABLE login_info (
        id                      bigserial                             NOT NULL,
        account_id              bigint                                NOT NULL,
        last_successful_login   timestamp,
        last_unsuccessful_login timestamp,
        login_attempt_counter   int         DEFAULT 0                 NOT NULL,
        ip_address              varchar(39),
        version                 bigint      DEFAULT 1                 NOT NULL
);

--------------------------------------------------------------------------------
--                             MOK Constraints                                --
--------------------------------------------------------------------------------
ALTER TABLE ONLY account
    ADD CONSTRAINT account_pk PRIMARY KEY (id),
    ADD CONSTRAINT email_unique UNIQUE (email);

ALTER TABLE ONLY password_reset_token
    ADD CONSTRAINT password_reset_token_pk PRIMARY KEY (id),
    ADD CONSTRAINT password_reset_token_account_id_fk FOREIGN KEY (account_id)
        REFERENCES account(id),
    ADD CONSTRAINT token_unique UNIQUE (token),
    ADD CONSTRAINT expiration_time_min_value
        CHECK (expiration_time > current_timestamp);

ALTER TABLE ONLY access_level
    ADD CONSTRAINT access_level_pk PRIMARY KEY (id),
    ADD CONSTRAINT account_id_level_unique UNIQUE (account_id, level),
    ADD CONSTRAINT access_level_account_id_fk FOREIGN KEY (account_id)
        REFERENCES account (id);

ALTER TABLE ONLY admin
    ADD CONSTRAINT admin_pk PRIMARY KEY (id),
    ADD CONSTRAINT admin_id_fk FOREIGN KEY (id) REFERENCES access_level (id);

ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_pk PRIMARY KEY (id),
    ADD CONSTRAINT employee_id_fk FOREIGN KEY (id) REFERENCES access_level (id),
    ADD CONSTRAINT employee_number_unique UNIQUE (employee_number);

ALTER TABLE ONLY candidate
    ADD CONSTRAINT candidate_pk PRIMARY KEY (id),
    ADD CONSTRAINT candidate_id_fk FOREIGN KEY (id) REFERENCES access_level (id);

ALTER TABLE ONLY login_info
    ADD CONSTRAINT login_info_pk PRIMARY KEY (id),
    ADD CONSTRAINT login_info_account_id_unique UNIQUE (account_id),
    ADD CONSTRAINT login_info_account_id_fk FOREIGN KEY (account_id)
        REFERENCES account (id);

--------------------------------------------------------------------------------
--                               Auth view                                    --
--------------------------------------------------------------------------------

CREATE VIEW auth_view (id, email, password, access_level)
AS SELECT row_number() OVER (PARTITION BY true), account.email,
          account.password, access_level."level"
   FROM account JOIN access_level ON account.id = access_level.account_id
   WHERE account.confirmed = true
     AND account.active = true
     AND access_level.active = true;

--------------------------------------------------------------------------------
--                                   MOL                                      --
--------------------------------------------------------------------------------

CREATE TABLE starship
(
    id                  bigserial            NOT NULL,
    name                varchar(32)          NOT NULL,
    crew_capacity       smallint             NOT NULL,
    maximum_weight      double precision     NOT NULL,
    fuel_capacity       double precision     NOT NULL,
    maximum_speed       double precision     NOT NULL,
    year_of_manufacture smallint             NOT NULL,
    operational         boolean DEFAULT true NOT NULL,
    version             bigint  DEFAULT 1    NOT NULL
);

CREATE TABLE offer
(
    id                 bigserial                     NOT NULL,
    starship_id        bigint                        NOT NULL,
    flight_start_time  timestamp                     NOT NULL,
    flight_end_time    timestamp                     NOT NULL,
    destination        varchar(256)                  NOT NULL,
    price              integer                       NOT NULL,
    description        varchar                       NOT NULL,
    hidden             boolean          DEFAULT true NOT NULL,
    open               boolean          DEFAULT true NOT NULL,
    total_cost         bigint                        NOT NULL,
    total_weight       double precision DEFAULT 0    NOT NULL,
    version            bigint           DEFAULT 1    NOT NULL
);

CREATE TABLE application
(
    id                  bigserial                                  NOT NULL,
    candidate_id        bigint                                     NOT NULL,
    offer_id            bigint                                     NOT NULL,
    weight              double precision                           NOT NULL,
    examination_code    varchar(1024)                              NOT NULL,
    motivational_letter varchar                                    NOT NULL,
    created_time        timestamp        DEFAULT current_timestamp NOT NULL,
    version             bigint           DEFAULT 1                 NOT NULL
);

CREATE TABLE review
(
    id             bigserial        NOT NULL,
    reviewer_id    bigint           NOT NULL,
    application_id bigint           NOT NULL,
    category_id    bigint           NOT NULL,
    version        bigint DEFAULT 1 NOT NULL
);

CREATE TABLE category
(
    id      bigserial        NOT NULL,
    name    varchar(16)      NOT NULL,
    version bigint DEFAULT 1 NOT NULL
);

--------------------------------------------------------------------------------
--                             MOL Constraints                                --
--------------------------------------------------------------------------------

ALTER TABLE starship
    ADD CONSTRAINT starship_pk PRIMARY KEY (id),
    ADD CONSTRAINT starship_name_unique UNIQUE (name);

ALTER TABLE offer
    ADD CONSTRAINT offer_pk PRIMARY KEY (id),
    ADD CONSTRAINT starship_id_fk FOREIGN KEY (starship_id) REFERENCES starship (id);

ALTER TABLE application
    ADD CONSTRAINT application_pk PRIMARY KEY (id),
    ADD CONSTRAINT candidate_id_offer_id_unique UNIQUE (candidate_id, offer_id),
    ADD CONSTRAINT candidate_id_fk FOREIGN KEY (candidate_id)
        REFERENCES candidate (id),
    ADD CONSTRAINT offer_id_fk FOREIGN KEY (offer_id) REFERENCES offer (id);

ALTER TABLE category
    ADD CONSTRAINT category_id_pk PRIMARY KEY (id);

ALTER TABLE review
    ADD CONSTRAINT review_pk PRIMARY KEY (id),
    ADD CONSTRAINT application_id_unique UNIQUE (application_id),
    ADD CONSTRAINT reviewer_id_fk FOREIGN KEY (reviewer_id)
        REFERENCES employee (id),
    ADD CONSTRAINT application_id_fk FOREIGN KEY (application_id)
        REFERENCES application (id),
    ADD CONSTRAINT category_id_fk FOREIGN KEY (category_id)
        REFERENCES category (id);

--------------------------------------------------------------------------------
--                           Business Constraints                             --
--------------------------------------------------------------------------------

ALTER TABLE starship
    ADD CONSTRAINT maximum_weight_value CHECK (maximum_weight > 0),
    ADD CONSTRAINT crew_capacity_value CHECK (crew_capacity > 0),
    ADD CONSTRAINT fuel_capacity_value CHECK (fuel_capacity > 0),
    ADD CONSTRAINT maximum_speed_value CHECK (maximum_speed > 0),
    ADD CONSTRAINT year_of_manufacture_min_value
        CHECK (year_of_manufacture > 2000),
    ADD CONSTRAINT year_of_manufacture_max_value
        CHECK (year_of_manufacture <= date_part('year', CURRENT_DATE));

ALTER TABLE offer
    ADD CONSTRAINT price_value CHECK (price > 0),
    ADD CONSTRAINT end_time_value CHECK (flight_end_time > flight_start_time),
    ADD CONSTRAINT total_cost_value CHECK (total_cost > 0),
    ADD CONSTRAINT total_weight_value CHECK (total_weight >= 0);

ALTER TABLE application
    ADD CONSTRAINT weight_value CHECK (weight > 0),
    ADD CONSTRAINT created_time_value CHECK (created_time <= current_timestamp);

--------------------------------------------------------------------------------
--                                 Indexes                                    --
--------------------------------------------------------------------------------

CREATE INDEX access_level_account_id_index ON access_level
    USING btree (account_id);
CREATE INDEX password_reset_token_account_id_index ON password_reset_token
    USING btree (account_id);
CREATE INDEX starship_id_index ON offer USING btree (starship_id);
CREATE INDEX offer_id_index ON application USING btree (offer_id);
CREATE INDEX candidate_id_index ON application USING btree (candidate_id);
CREATE INDEX reviewer_id_index ON review USING btree (reviewer_id);
CREATE INDEX category_id_index ON review USING hash (category_id);

--------------------------------------------------------------------------------
--                                Privileges                                  --
--------------------------------------------------------------------------------

REVOKE ALL ON TABLE account FROM PUBLIC;
REVOKE ALL ON TABLE password_reset_token FROM PUBLIC;
REVOKE ALL ON TABLE access_level FROM PUBLIC;
REVOKE ALL ON TABLE admin FROM PUBLIC;
REVOKE ALL ON TABLE employee FROM PUBLIC;
REVOKE ALL ON TABLE candidate FROM PUBLIC;
REVOKE ALL ON TABLE login_info FROM PUBLIC;
REVOKE ALL ON TABLE starship FROM PUBLIC;
REVOKE ALL ON TABLE offer FROM PUBLIC;
REVOKE ALL ON TABLE application FROM PUBLIC;
REVOKE ALL ON TABLE review FROM PUBLIC;
REVOKE ALL ON TABLE category FROM PUBLIC;
REVOKE ALL ON TABLE auth_view FROM PUBLIC;


GRANT SELECT, UPDATE, INSERT ON account TO ssbd03mok;
GRANT SELECT, INSERT, DELETE ON password_reset_token TO ssbd03mok;
GRANT SELECT, UPDATE, INSERT ON access_level TO ssbd03mok;
GRANT SELECT, UPDATE, INSERT ON admin TO ssbd03mok;
GRANT SELECT, UPDATE, INSERT ON employee TO ssbd03mok;
GRANT SELECT, UPDATE, INSERT ON candidate TO ssbd03mok;
GRANT SELECT, UPDATE, INSERT ON login_info TO ssbd03mok;
GRANT SELECT, UPDATE, INSERT ON starship TO ssbd03mol;
GRANT SELECT, UPDATE, INSERT, DELETE ON offer TO ssbd03mol;
GRANT SELECT, UPDATE, INSERT, DELETE ON application TO ssbd03mol;
GRANT SELECT, UPDATE, INSERT ON review TO ssbd03mol;
GRANT SELECT, UPDATE, INSERT ON category TO ssbd03mol;
GRANT SELECT ON access_level TO ssbd03mol;
GRANT SELECT ON login_info TO ssbd03mol;
GRANT SELECT ON account TO ssbd03mol;
GRANT SELECT ON candidate TO ssbd03mol;
GRANT SELECT ON employee TO ssbd03mol;
GRANT SELECT ON auth_view TO ssbd03auth;


GRANT USAGE, SELECT ON SEQUENCE account_id_seq TO ssbd03mok;
GRANT USAGE, SELECT ON SEQUENCE password_reset_token_id_seq TO ssbd03mok;
GRANT USAGE, SELECT ON SEQUENCE access_level_id_seq TO ssbd03mok;
GRANT USAGE, SELECT ON SEQUENCE employee_employee_number_seq TO ssbd03mok;
GRANT USAGE, SELECT ON SEQUENCE login_info_id_seq TO ssbd03mok;

GRANT USAGE, SELECT ON SEQUENCE starship_id_seq TO ssbd03mol;
GRANT USAGE, SELECT ON SEQUENCE offer_id_seq TO ssbd03mol;
GRANT USAGE, SELECT ON SEQUENCE application_id_seq TO ssbd03mol;
GRANT USAGE, SELECT ON SEQUENCE review_id_seq TO ssbd03mol;
GRANT USAGE, SELECT ON SEQUENCE category_id_seq TO ssbd03mol;

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
        'Niezapomniana podróż w bezkresną otchłań kosmosu. Niepowtarzalna okazja do uczestnictwa w pierwszej turystycznej misji kosmicznej.',
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
