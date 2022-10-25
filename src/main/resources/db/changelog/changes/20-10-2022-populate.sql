--liquibase formatted sql

--changeset edzvonik:2

insert into "user"(id, name, email, base_currency)
values (1, 'Evgenii', 'evgenii@cashflow.com', 'RUB');
select setval('seq_user', 1);

insert into account(id, title, currency, user_id)
values (1, 'Cash', 'RUB', 1),
       (2, 'Card', 'USD', 1);
select setval('seq_account', 2);

insert into category(id, title)
values (1, 'Work'),
       (2, 'Pets'),
       (3, 'Car');
select setval('seq_category', 3);

insert into account_category(account_id, category_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3);

insert into transaction(id, amount, type, date, comment, account_id, category_id)
values -- 08.22
       (1, '5000.61', 'INCOME', '2022-08-02', 'Зарплата', 1, 1),
       (2, '2500.50', 'EXPENSE', '2022-08-03', 'Новые наушники', 1, 1),
       (3, '20.39', 'EXPENSE', '2022-08-03', 'Корм для кошек', 1, 2),
       (4, '150.10', 'EXPENSE', '2022-08-15', 'Вызов ветеринара', 1, 2),
       (5, '250.11', 'INCOME', '2022-08-17', 'Подработка в такси', 1, 3),
       (6, '102.99', 'EXPENSE', '2022-10-15', 'Заправка', 1, 3),
       -- 09.22
       (7, '500.51', 'INCOME', '2022-09-02', 'Зарплата', 1, 1),
       (8, '32.69', 'EXPENSE', '2022-09-03', 'Ремонт окна', 1, 1),
       (9, '15.99', 'EXPENSE', '2022-09-05', 'Успокоительное для кошек', 1, 2),
       (10, '32.01', 'EXPENSE', '2022-09-16', 'Груминг', 1, 2),
       (11, '100.00', 'INCOME', '2022-09-19', 'Подработка в такси', 1, 3),
       (12, '59.12', 'EXPENSE', '2022-09-21', 'Заправка', 1, 3),
       -- 10.22
       (13, '620.59', 'INCOME', '2022-10-02', 'Зарплата', 1, 1),
       (14, '120.15', 'INCOME', '2022-10-05', 'Продажа кресла', 1, 1),
       (15, '9.20', 'EXPENSE', '2022-10-06', 'Поводок', 1, 2),
       (16, '8.30', 'EXPENSE', '2022-10-13', 'Шампунь для собак', 1, 2),
       (17, '450.00', 'INCOME', '2022-10-20', 'Подработка в службе доставки', 1, 3),
       (18, '40.24', 'EXPENSE', '2022-10-21', 'Заправка', 1, 3);






