CREATE TABLE products (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(500),
    preco DECIMAL(15,2) NOT NULL,
    estoque INT NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_products_categoria ON products(categoria);

ALTER TABLE products
    ADD COLUMN nome_lower VARCHAR(150) GENERATED ALWAYS AS (LOWER(nome)) STORED,
    ADD INDEX idx_products_nome_lower (nome_lower);
