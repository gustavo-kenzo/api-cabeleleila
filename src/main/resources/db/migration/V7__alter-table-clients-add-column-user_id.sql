ALTER TABLE clients
    ADD COLUMN user_id INT UNIQUE,
    ADD CONSTRAINT fk_clients_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL;