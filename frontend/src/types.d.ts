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