CREATE TABLE IF NOT EXISTS orders (
    order_id TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,
    address_id TEXT NOT NULL,
    order_status INTEGER NOT NULL,
    total_price DECIMAL(10,2) NOT NULL DEFAULT 0,
    order_tracking_code VARCHAR(6) NOT NULL,
    created_at TIMESTAMP DEFAULT (CURRENT_TIMESTAMP) NOT NULL,
    updated_at TIMESTAMP DEFAULT (CURRENT_TIMESTAMP) NOT NULL,

    CONSTRAINT fk_order_user
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE

    CONSTRAINT fk_order_address
    FOREIGN KEY (address_id) REFERENCES address(address_id)
    ON DELETE CASCADE
);


CREATE TABLE order_items (
     order_item_id TEXT PRIMARY KEY,
     order_id TEXT NOT NULL,
     item_id TEXT NOT NULL,
     quantity INTEGER NOT NULL,
     unit_price DECIMAL(10,2) NOT NULL,
     subtotal DECIMAL(10,2) NOT NULL,
     created_at TIMESTAMP DEFAULT (CURRENT_TIMESTAMP) NOT NULL,
     updated_at TIMESTAMP DEFAULT (CURRENT_TIMESTAMP) NOT NULL,

     FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
     FOREIGN KEY (item_id) REFERENCES itens(item_id) ON DELETE RESTRICT
);