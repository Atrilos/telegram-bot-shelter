-- liquibase formatted sql

-- changeset HARD:1675587301684-5
ALTER TABLE shelter_details
    ADD about VARCHAR(255) NOT NULL;
-- rollback alter table shelter_details drop about;

-- changeset HARD:1675587301684-7
ALTER TABLE shelter_details
    ADD cat_home OID NOT NULL;
-- rollback alter table shelter_details drop cat_home;

-- changeset HARD:1675587301684-8
ALTER TABLE shelter_details
    ADD communication OID NOT NULL;
-- rollback alter table shelter_details drop communication;

-- changeset HARD:1675587301684-10
ALTER TABLE shelter_details
    ADD disabled_dog_home OID NOT NULL;
-- rollback alter table shelter_details drop disabled_dog_home;

-- changeset HARD:1675587301684-11
ALTER TABLE shelter_details
    ADD dog_handlers OID NOT NULL;
-- rollback alter table shelter_details drop dog_handlers;

-- changeset HARD:1675587301684-12
ALTER TABLE shelter_details
    ADD dog_home OID NOT NULL;
-- rollback alter table shelter_details drop dog_home;

-- changeset HARD:1675587301684-15
ALTER TABLE shelter_details
    ADD is_cat_shelter BOOLEAN DEFAULT FALSE;
-- rollback alter table shelter_details drop is_cat_shelter;

-- changeset HARD:1675587301684-20
ALTER TABLE shelter_details
    ADD meeting_rules OID NOT NULL;
-- rollback alter table shelter_details drop meeting_rules;

-- changeset HARD:1675587301684-21
ALTER TABLE shelter_details
    ADD open_hours VARCHAR(255) NOT NULL;
-- rollback alter table shelter_details drop open_hours;

-- changeset HARD:1675587301684-22
ALTER TABLE shelter_details
    ADD papers OID NOT NULL;
-- rollback alter table shelter_details drop papers;

-- changeset HARD:1675587301684-23
ALTER TABLE shelter_details
    ADD pup_home OID NOT NULL;
-- rollback alter table shelter_details drop pup_home;

-- changeset HARD:1675587301684-24
ALTER TABLE shelter_details
    ADD refusal_cause OID NOT NULL;
-- rollback alter table shelter_details drop refusal_cause;

-- changeset HARD:1675587301684-25
ALTER TABLE shelter_details
    ADD transport OID NOT NULL;
-- rollback alter table shelter_details drop transport;

-- changeset HARD:1675587301684-26
ALTER TABLE shelter_details
    DROP COLUMN working_hours;
-- rollback alter table shelter_details add working_hours VARCHAR(255) NOT NULL;

-- changeset HARD:1675589377562-1
ALTER TABLE shelter_details
    ALTER COLUMN cat_home DROP NOT NULL;
-- rollback alter table shelter_details ALTER COLUMN cat_home SET NOT NULL;

-- changeset HARD:1675589377562-3
ALTER TABLE shelter_details
    ALTER COLUMN disabled_dog_home DROP NOT NULL;
-- rollback alter table shelter_details ALTER COLUMN disabled_dog_home SET NOT NULL;

-- changeset HARD:1675589377562-4
ALTER TABLE shelter_details
    ALTER COLUMN dog_home DROP NOT NULL;
-- rollback alter table shelter_details ALTER COLUMN dog_home SET NOT NULL;

-- changeset HARD:1675589377562-9
ALTER TABLE shelter_details
    ALTER COLUMN map_url SET NOT NULL;
-- rollback alter table shelter_details ALTER COLUMN map_url DROP NOT NULL;

-- changeset HARD:1675589377562-10
ALTER TABLE shelter_details
    ALTER COLUMN pup_home DROP NOT NULL;
-- rollback alter table shelter_details ALTER COLUMN pup_home SET NOT NULL;

-- changeset HARD:1675589377562-11
ALTER TABLE shelter_details
    ALTER COLUMN safety_instructions SET NOT NULL;
-- rollback alter table shelter_details ALTER COLUMN safety_instructions DROP NOT NULL;



