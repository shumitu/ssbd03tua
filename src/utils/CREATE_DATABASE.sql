-- ------------------------------------------------------------------------------
--                               Initialization                                --
-- ------------------------------------------------------------------------------

USE ssbd03;
CREATE DATABASE IF NOT EXISTS ssbd03 CHARACTER SET UTF8 COLLATE utf8_bin;

-- ------------------------------------------------------------------------------
--                                 Cleaning                                   --
-- ------------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS password_reset_token;
DROP TABLE IF EXISTS access_level;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS candidate;
DROP TABLE IF EXISTS login_info;
DROP TABLE IF EXISTS application;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS offer;
DROP TABLE IF EXISTS starship;
DROP VIEW IF EXISTS auth_view;
SET FOREIGN_KEY_CHECKS = 1;

-- ------------------------------------------------------------------------------
--                                     MOK                                     --
-- ------------------------------------------------------------------------------

CREATE TABLE account (
     id                       bigint	   PRIMARY KEY AUTO_INCREMENT 	NOT NULL,
     password                 char(64)                   				NOT NULL,
     email                    varchar(254) UNIQUE              			NOT NULL,
     motto                    varchar(128)               				NOT NULL,
     confirmed                boolean      DEFAULT false 				NOT NULL,
     active                   boolean      DEFAULT true  				NOT NULL,
     password_change_required boolean      DEFAULT true  				NOT NULL,
     version                  bigint       DEFAULT 1     				NOT NULL
);

CREATE TABLE password_reset_token (
      id              bigint	  PRIMARY KEY AUTO_INCREMENT          NOT NULL,
      account_id      bigint                                          NOT NULL,
      token           char(64)    UNIQUE                              NOT NULL,
      expiration_time timestamp   DEFAULT current_timestamp			  NOT NULL,
      version         bigint      DEFAULT 1                           NOT NULL,
      CONSTRAINT password_reset_token_account_id_fk FOREIGN KEY (account_id)
	    REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE access_level (
      id         bigint	 PRIMARY KEY AUTO_INCREMENT NOT NULL,
      account_id bigint               				NOT NULL,
      level      varchar(16)          				NOT NULL,
      active     boolean DEFAULT true 				NOT NULL,
      version    bigint  DEFAULT 1    				NOT NULL,
      CONSTRAINT access_level_account_id_fk FOREIGN KEY (account_id)
				 REFERENCES account (id) ON DELETE CASCADE,
	  CONSTRAINT account_id_level_unique UNIQUE (account_id, level)
);

CREATE TABLE admin (
       id      bigint PRIMARY KEY 	  	NOT NULL,
       version bigint DEFAULT 1 		NOT NULL,
       CONSTRAINT admin_id_fk FOREIGN KEY (id)
		REFERENCES access_level (id) ON DELETE CASCADE
);

CREATE TABLE employee (
      id              bigint PRIMARY KEY    		NOT NULL,
      employee_number bigint AUTO_INCREMENT UNIQUE  NOT NULL,
      version         bigint DEFAULT 1				NOT NULL,
      CONSTRAINT employee_id_fk FOREIGN KEY (id)
		REFERENCES access_level (id) ON DELETE CASCADE
);

CREATE TABLE candidate (
       id         bigint PRIMARY KEY        NOT NULL,
       first_name varchar(16)      			NOT NULL,
       last_name  varchar(32)      			NOT NULL,
       version    bigint DEFAULT 1 			NOT NULL,
       CONSTRAINT candidate_id_fk FOREIGN KEY (id)
		REFERENCES access_level (id) ON DELETE CASCADE
);

CREATE TABLE login_info (
        id                      bigint 		PRIMARY KEY AUTO_INCREMENT NOT NULL,
        account_id              bigint      UNIQUE                     NOT NULL,
        last_successful_login   timestamp,
        last_unsuccessful_login timestamp,
        login_attempt_counter   int         DEFAULT 0                  NOT NULL,
        ip_address              varchar(39),
        version                 bigint      DEFAULT 1                  NOT NULL,
        CONSTRAINT login_info_account_id_fk FOREIGN KEY (account_id)
          REFERENCES account (id) ON DELETE CASCADE
);

-- ------------------------------------------------------------------------------
--                               Auth view                                    --
-- ------------------------------------------------------------------------------

CREATE VIEW auth_view (id, email, password, access_level)
AS SELECT row_number() OVER (PARTITION BY true), account.email,
          account.password, access_level.level
   FROM account JOIN access_level ON account.id = access_level.account_id
   WHERE account.confirmed = true
     AND account.active = true
     AND access_level.active = true;

-- ------------------------------------------------------------------------------
--                                    MOL                                      --
-- ------------------------------------------------------------------------------

CREATE TABLE starship
(
    id                  bigint 		PRIMARY KEY AUTO_INCREMENT		 NOT NULL,
    name                varchar(32) UNIQUE   						 NOT NULL,
    crew_capacity       smallint    UNSIGNED						 NOT NULL,
    maximum_weight      double 		UNSIGNED   						 NOT NULL,
    fuel_capacity       double 		UNSIGNED						 NOT NULL,
    maximum_speed       double 		UNSIGNED						 NOT NULL,
    year_of_manufacture smallint    								 NOT NULL,
    operational         boolean 	DEFAULT true					 NOT NULL,
    version             bigint  	DEFAULT 1    					 NOT NULL,
    CONSTRAINT maximum_weight_value CHECK (maximum_weight > 0),
    CONSTRAINT crew_capacity_value CHECK (crew_capacity > 0),
    CONSTRAINT fuel_capacity_value CHECK (fuel_capacity > 0),
    CONSTRAINT maximum_speed_value CHECK (maximum_speed > 0)
);

CREATE TABLE offer
(
    id                 bigint 			PRIMARY KEY AUTO_INCREMENT      NOT NULL,
    starship_id        bigint                        					NOT NULL,
    flight_start_time  timestamp                     					NOT NULL,
    flight_end_time    timestamp                     					NOT NULL,
    destination        varchar(256)                  					NOT NULL,
    price              integer          UNSIGNED       					NOT NULL,
    description        text                       						NOT NULL,
    hidden             boolean          DEFAULT true 					NOT NULL,
    open               boolean          DEFAULT true 					NOT NULL,
    total_cost         bigint           UNSIGNED             			NOT NULL,
    total_weight       double 			UNSIGNED DEFAULT 0    			NOT NULL,
    version            bigint           DEFAULT 1    					NOT NULL,
    CONSTRAINT starship_id_fk FOREIGN KEY (starship_id)
		REFERENCES starship (id) ON DELETE CASCADE,
    CONSTRAINT price_value CHECK (price > 0),
    CONSTRAINT end_time_value CHECK (flight_end_time > flight_start_time),
    CONSTRAINT total_cost_value CHECK (total_cost > 0),
    CONSTRAINT total_weight_value CHECK (total_weight >= 0)
);


CREATE TABLE application
(
    id                  bigint 			PRIMARY KEY AUTO_INCREMENT NOT NULL,
    candidate_id        bigint                                     NOT NULL,
    offer_id            bigint                                     NOT NULL,
    weight              double 			UNSIGNED                   NOT NULL,
    examination_code    varchar(1024)                              NOT NULL,
    motivational_letter text                                       NOT NULL,
    created_time        timestamp        DEFAULT current_timestamp NOT NULL,
    version             bigint           DEFAULT 1                 NOT NULL,
    CONSTRAINT candidate_id_offer_id_unique UNIQUE (candidate_id, offer_id),
    CONSTRAINT candidate_id_fkey FOREIGN KEY (candidate_id)
        REFERENCES candidate (id) ON DELETE CASCADE,
	CONSTRAINT offer_id_fk FOREIGN KEY (offer_id)
		REFERENCES offer (id) ON DELETE CASCADE,
	CONSTRAINT weight_value CHECK (weight > 0)
);

CREATE TABLE category
(
    id      bigint PRIMARY KEY AUTO_INCREMENT   NOT NULL,
    name    varchar(16)      					NOT NULL,
    version bigint DEFAULT 1 					NOT NULL
);

CREATE TABLE review
(
    id             bigint PRIMARY KEY AUTO_INCREMENT    NOT NULL,
    reviewer_id    bigint           					NOT NULL,
    application_id bigint UNIQUE          				NOT NULL,
    category_id    bigint           					NOT NULL,
    version        bigint DEFAULT 1 					NOT NULL,
    CONSTRAINT reviewer_id_fk FOREIGN KEY (reviewer_id)
        REFERENCES employee (id) ON DELETE CASCADE,
    CONSTRAINT application_id_fk FOREIGN KEY (application_id)
        REFERENCES application (id) ON DELETE CASCADE,
    CONSTRAINT category_id_fk FOREIGN KEY (category_id)
        REFERENCES category (id) ON DELETE CASCADE
);


-- ------------------------------------------------------------------------------
--                                  Indexes                                    --
-- ------------------------------------------------------------------------------

CREATE INDEX access_level_account_id_index ON access_level(account_id)
    USING btree;
CREATE INDEX password_reset_token_account_id_index ON password_reset_token(account_id)
    USING btree;
CREATE INDEX starship_id_index ON offer (starship_id) USING btree;
CREATE INDEX offer_id_index ON application (offer_id) USING btree;
CREATE INDEX candidate_id_index ON application (candidate_id) USING btree;
CREATE INDEX reviewer_id_index ON review (reviewer_id) USING btree;
CREATE INDEX category_id_index ON review (category_id) USING hash;

-- ------------------------------------------------------------------------------
--                                 Privileges                                  --
-- ------------------------------------------------------------------------------

CREATE USER 'ssbd03mok'@'%' IDENTIFIED BY 'moK@ssbd03';
CREATE USER 'ssbd03mol'@'%' IDENTIFIED BY 'moL@ssbd03';
CREATE USER 'ssbd03auth'@'%' IDENTIFIED BY 'autH@ssbd03';

/*
REVOKE ALL PRIVILEGES ON . FROM 'ssbd03mok'@'%';
REVOKE ALL PRIVILEGES ON . FROM 'ssbd03mol'@'%';
REVOKE ALL PRIVILEGES ON . FROM 'ssbd03auth'@'%';
*/

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

GRANT USAGE, SELECT ON `ssbd03`.`account` TO 'ssbd03mok'@'%' WITH GRANT OPTION;
GRANT USAGE, SELECT ON `ssbd03`.`password_reset_token` TO 'ssbd03mok'@'%' WITH GRANT OPTION;
GRANT USAGE, SELECT ON `ssbd03`.`access_level` TO 'ssbd03mok'@'%' WITH GRANT OPTION;
GRANT USAGE, SELECT ON `ssbd03`.`employee` TO 'ssbd03mok'@'%' WITH GRANT OPTION;
GRANT USAGE, SELECT ON `ssbd03`.`login_info` TO 'ssbd03mok'@'%' WITH GRANT OPTION;

GRANT USAGE, SELECT ON `ssbd03`.`starship` TO 'ssbd03mol'@'%' WITH GRANT OPTION;
GRANT USAGE, SELECT ON `ssbd03`.`offer` TO 'ssbd03mol'@'%' WITH GRANT OPTION;
GRANT USAGE, SELECT ON `ssbd03`.`application` TO 'ssbd03mol'@'%' WITH GRANT OPTION;
GRANT USAGE, SELECT ON `ssbd03`.`review` TO 'ssbd03mol'@'%' WITH GRANT OPTION;
GRANT USAGE, SELECT ON `ssbd03`.`category` TO 'ssbd03mol'@'%' WITH GRANT OPTION;

-- ------------------------------------------------------------------------------
--                          Initialization records                             --
-- ------------------------------------------------------------------------------

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
VALUES (1, 75029375, ADDTIME(current_timestamp, "10:35"), ADDTIME(current_timestamp, "312:13"),
        'Odległe zakątki Marsa skrywające odwieczne tajemnice budowniczych piramid.',
        'Niezapomniana podróż w bezkresną otchłań kosmosu. Niepowtarzalna okazja do uczestnictwa w pierwszej turystycznej misji kosmicznej.',
        47507293075, 81.4);
INSERT INTO offer (starship_id, price, flight_start_time, flight_end_time,
                   destination, description, total_cost, total_weight, hidden, open)
VALUES (1, 75029375, ADDTIME(current_timestamp, "1:1:35"), ADDTIME(current_timestamp, "2:15:13"),
        'Venus',
        'W sumie zwykła wycieczka.',
        42137293075, 0, false, true);
INSERT INTO offer (starship_id, price, flight_start_time, flight_end_time,
                   destination, description, total_cost, total_weight, hidden, open)
VALUES (1, 705029375, ADDTIME(current_timestamp, "1:3:35"), ADDTIME(current_timestamp, "2:59:13"),
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
