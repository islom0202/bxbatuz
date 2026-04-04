--liquibase formatted sql

--changeset antifraud:012-add-new-column

alter table linked_users
add column concurs_id bigint,
add column user_phone varchar,
add column user_device_id varchar,
ADD COLUMN latitude DOUBLE PRECISION,
ADD COLUMN longitude DOUBLE PRECISION,
ADD COLUMN ip_latitude DOUBLE PRECISION,
ADD COLUMN ip_longitude DOUBLE PRECISION;