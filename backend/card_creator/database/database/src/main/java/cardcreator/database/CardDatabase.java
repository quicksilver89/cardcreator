package cardcreator.database;

import cardcreator.data.Card;
import cardcreator.data.CardAndId;
import cardcreator.data.ErrorMessageException;

import java.util.List;

public interface CardDatabase
{
    List<CardAndId> listCards() throws ErrorMessageException;

    Card getCard( String id ) throws ErrorMessageException;

    String addNewCard( Card card ) throws ErrorMessageException;

    boolean editCard( String id, Card oldCard, Card newCard ) throws ErrorMessageException;

    boolean deleteCard( String id, Card card ) throws ErrorMessageException;
}

