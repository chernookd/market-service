
CREATE TABLE catalogue.products
(

    id  SERIAL PRIMARY KEY,
    name    VARCHAR(50) NOT NULL CHECK (length(trim(name)) >= 3),
    description VARCHAR(1000) NOT NULL
)