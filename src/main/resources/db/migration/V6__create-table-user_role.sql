CREATE TABLE user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_role
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_role_user
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);