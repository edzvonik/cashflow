--liquibase formatted sql

--changeset edzvonik:3

alter table transaction alter column account_id drop not null;
alter table transaction alter column category_id drop not null;