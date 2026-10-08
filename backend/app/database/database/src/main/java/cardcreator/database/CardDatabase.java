package cardcreator.database;

import cardcreator.data.Card;
import cardcreator.data.CardAndId;
import cardcreator.data.ErrorMessageException;

import java.util.List;

public interface CardDatabase
{
    List<CardAndId> listCards( String userId ) throws ErrorMessageException;

    Card getCard( String userId, String cardId ) throws ErrorMessageException;

    String addNewCard( String userId, Card card ) throws ErrorMessageException;

    boolean editCard( String userId, String cardId, Card oldCard, Card newCard ) throws ErrorMessageException;

    boolean deleteCard( String userId, String cardId, Card card ) throws ErrorMessageException;
}

