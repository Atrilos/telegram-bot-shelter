-- liquibase formatted sql

-- changeset HARD:1675588023618-6
ALTER TABLE users
    ADD adoption_day INTEGER;
-- rollback alter table users drop adoption_day;

-- changeset HARD:1675588023618-13
ALTER TABLE users
    ADD is_cat_adopter BOOLEAN DEFAULT FALSE;
-- rollback alter table users drop is_cat_adopter;

-- changeset HARD:1675588023618-14
ALTER TABLE users
    ADD is_cat_adopter_trial BOOLEAN DEFAULT FALSE;
-- rollback alter table users drop is_cat_adopter_trial;

-- changeset HARD:1675588023618-16
ALTER TABLE users
    ADD is_dog_adopter BOOLEAN DEFAULT FALSE;
-- rollback alter table users drop is_dog_adopter;

-- changeset HARD:1675588023618-17
ALTER TABLE users
    ADD is_dog_adopter_trial BOOLEAN DEFAULT FALSE;
-- rollback alter table users drop is_dog_adopter_trial;

-- changeset HARD:1675588023618-18
ALTER TABLE users
    ADD last_photo_report_day INTEGER;
-- rollback alter table users drop last_photo_report_day;

-- changeset HARD:1675588023618-19
ALTER TABLE users
    ADD last_report_day INTEGER;
-- rollback alter table users drop last_report_day;

-- changeset HARD:1675588023618-2
ALTER TABLE users
    ALTER COLUMN first_name SET NOT NULL;
-- rollback alter table users ALTER COLUMN first_name drop not null;

-- changeset HARD:1675589794618-13
ALTER TABLE users
    ADD current_shelter VARCHAR(255);
-- rollback alter table users drop current_shelter;

-- changeset HARD:1675601299593-1
ALTER TABLE users
    ALTER COLUMN last_report_day TYPE TIMESTAMP WITHOUT TIME ZONE
        using to_timestamp(last_report_day);
-- rollback alter table alter column last_report_day
-- rollback type INTEGER using (extract(epoch from last_report_day)::integer);

-- changeset HARD:1675601299593-2
ALTER TABLE users
    ALTER COLUMN last_photo_report_day TYPE TIMESTAMP WITHOUT TIME ZONE
        using to_timestamp(last_photo_report_day);
-- rollback alter table alter column last_photo_report_day
-- rollback type INTEGER using (extract(epoch from last_photo_report_day)::integer);

-- changeset HARD:1675601299593-3
ALTER TABLE users
    ALTER COLUMN adoption_day TYPE TIMESTAMP WITHOUT TIME ZONE
        using to_timestamp(adoption_day);
-- rollback alter table alter column adoption_day
-- rollback type INTEGER using (extract(epoch from adoption_day)::integer);