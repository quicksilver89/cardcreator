export interface Card
{
    name: string;
    cost: string;
    text: string;
}

export interface CardAndId
{
    id: string;
    card: Card;
}

export interface EditCard
{
    id: string;
    oldCard: Card;
    newCard: Card;
}