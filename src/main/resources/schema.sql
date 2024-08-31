CREATE TABLE company_user (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              login VARCHAR(255) NOT NULL,
                              CONSTRAINT company_user_pkey PRIMARY KEY (id),
                              CONSTRAINT company_user_uniq_login UNIQUE (login)
);

CREATE TABLE patient_profile (
                                 id BIGINT NOT NULL AUTO_INCREMENT,
                                 first_name VARCHAR(255),
                                 last_name VARCHAR(255),
                                 old_client_guid VARCHAR(255),
                                 status_id SMALLINT NOT NULL,
                                 CONSTRAINT patient_profile_pkey PRIMARY KEY (id)
);

CREATE TABLE patient_note (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              created_date_time TIMESTAMP NOT NULL,
                              last_modified_date_time TIMESTAMP NOT NULL,
                              created_by_user_id BIGINT,
                              last_modified_by_user_id BIGINT,
                              note VARCHAR(4000),
                              patient_id BIGINT NOT NULL,
                              note_guid VARCHAR(255),
                              CONSTRAINT patient_note_pkey PRIMARY KEY (id),
                              CONSTRAINT fk_pat_note_to_modifyed_user FOREIGN KEY (last_modified_by_user_id) REFERENCES company_user(id),
                              CONSTRAINT fkpat_note_to_created_user FOREIGN KEY (created_by_user_id) REFERENCES company_user(id),
                              CONSTRAINT fk_pat_note_to_patient FOREIGN KEY (patient_id) REFERENCES patient_profile(id)
);