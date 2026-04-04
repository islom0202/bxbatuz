--liquibase formatted sql

--changeset antifraud:010-add-new-table

create table concurs(
  id BIGSERIAL PRIMARY KEY,
  name varchar,
  description varchar,
  admin_id bigint,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at timestamp
);

--changeset antifraud:011-add-new-column
alter table links
add column concurs_id bigint;