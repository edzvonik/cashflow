DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction
(
    transaction_id SERIAL PRIMARY KEY,
    created_at     DATE NOT NULL DEFAULT CURRENT_DATE,
    amount         NUMERIC NOT NULL,
    comment        VARCHAR
);

INSERT INTO transaction(amount, comment)
VALUES ('259.9', 'Home'),
       ('59.9', 'Pets'),
       ('9.9', 'Car');