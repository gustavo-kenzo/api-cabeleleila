CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    service_id INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    schedule_at TIMESTAMP NOT NULL,
    description VARCHAR(500),
    status ENUM('PENDING','CONFIRMED','COMPLETED','CANCELED','NO_SHOW') DEFAULT 'PENDING',

    CONSTRAINT fk_appointments_client
        FOREIGN KEY(client_id)
        REFERENCES clients(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_appointments_service
        FOREIGN KEY (service_id)
        REFERENCES services(id)
        ON DELETE RESTRICT
);