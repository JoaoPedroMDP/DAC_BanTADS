CREATE TABLE IF NOT EXISTS login (
    id SERIAL PRIMARY KEY,
    password TEXT NOT NULL,
    email VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    role VARCHAR(255) NOT NULL
);

INSERT INTO login(password,email,user_id,role) values  (
    -- password: teste
    'NVX9Z4K+DSFmCvflMzBXQQqPpZKy5/Un+lbGsHUUcGc=',
    'admin@admin.com',
    1,
    'ADMIN'
);
