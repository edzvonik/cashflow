--liquibase formatted sql

--changeset edzvonik:4

CREATE SEQUENCE seq_roles START WITH 1 INCREMENT BY 50;

CREATE TABLE roles
(
    id BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR
);

CREATE TABLE user_roles
(
  user_id BIGINT NOT NULL,
  CONSTRAINT fk_user_id
    FOREIGN KEY (user_id)
        REFERENCES "user" (id),
  role_id BIGINT NOT NULL,
  CONSTRAINT fk_role_id
    FOREIGN KEY (role_id)
        REFERENCES roles (id)
);

INSERT INTO roles VALUES (1, 'ADMIN');
SELECT SETVAL('seq_roles', 1);


