CREATE TABLE address (
   address_id VARCHAR(36) PRIMARY KEY,
   user_id VARCHAR(36) NOT NULL,
   street VARCHAR(255) NOT NULL,
   number INTEGER NOT NULL,
   city VARCHAR(100) NOT NULL,
   state VARCHAR(2) NOT NULL,

   CONSTRAINT fk_addresses_user
       FOREIGN KEY (user_id) REFERENCES users(user_id)
           ON DELETE CASCADE
);