package cardcreator.database;

import cardcreator.data.Card;
import cardcreator.data.CardAndId;

import java.util.List;

public interface CardDatabase
{
    List<CardAndId> listCards( String userId ) throws Exception;

    Card getCard( String userId, String cardId ) throws Exception;

    String addNewCard( String userId, Card card ) throws Exception;

    boolean editCard( String userId, String cardId, Card oldCard, Card newCard ) throws Exception;

    boolean deleteCard( String userId, String cardId, Card card ) throws Exception;
}
