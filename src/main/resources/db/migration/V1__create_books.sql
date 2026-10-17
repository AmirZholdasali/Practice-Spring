CREATE TABLE books (
                       id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       pages INT NOT NULL
);