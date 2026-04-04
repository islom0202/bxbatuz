--liquibase formatted sql

--changeset antifraud:013-add-new-column

alter table linked_users
add column is_fraud boolean default false;