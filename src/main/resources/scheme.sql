CREATE TABLE person (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name VARCHAR(512) UNIQUE NOT NULL,
    birthday_year INT
);

CREATE TABLE book (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(512) NOT NULL,
    author VARCHAR(512) NOT NULL,
    publish_year INT,
    owner_id INT REFERENCES person(id) ON DELETE CASCADE ON UPDATE CASCADE
);