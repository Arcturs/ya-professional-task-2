CREATE TABLE groups
(
    id        SERIAL PRIMARY KEY NOT NULL,
    name      TEXT               NOT NULL UNIQUE,
    parent_id SERIAL REFERENCES groups (id),
    UNIQUE (name, parent_id)
);

CREATE TABLE students
(
    id       SERIAL PRIMARY KEY NOT NULL,
    name     TEXT               NOT NULL,
    email    TEXT               NOT NULL UNIQUE,
    group_id SERIAL REFERENCES groups (id),
    UNIQUE (name, group_id)
);