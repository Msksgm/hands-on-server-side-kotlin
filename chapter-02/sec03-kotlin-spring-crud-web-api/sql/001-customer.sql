DROP TABLE IF EXISTS customer;

CREATE TABLE IF NOT EXISTS customer (
    id SERIAL,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

INSERT INTO
    customer(first_name, last_name)
VALUES
    ('Alice', 'Sample1'), ('Bob', 'Sample2');
