CREATE TABLE cards (
   card_id UUID NOT NULL DEFAULT gen_random_uuid(),
   user_id VARCHAR(36) NOT NULL,
   card_name TEXT NOT NULL,
   card_cost TEXT NOT NULL,
   card_text TEXT NOT NULL,
   CONSTRAINT pk_card PRIMARY KEY (card_id)
);

CREATE INDEX idx_cards_user_id ON cards(user_id);