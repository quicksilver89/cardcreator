package cardcreator.carddata.database;

import cardcreator.carddata.data.Card;
import cardcreator.carddata.data.CardAndId;

import java.util.List;

public interface CardDatabase
{
    CardDatabase DB = new DynamoDbCardDatabase();

    List<CardAndId> listCards();

    Card getCard( String id );

    String addNewCard( Card card );

    boolean editCard( String id, Card oldCard, Card newCard );

    boolean deleteCard( String id, Card card );
}
