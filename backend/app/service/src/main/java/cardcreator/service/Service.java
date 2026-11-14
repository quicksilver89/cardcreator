package cardcreator.service;

import cardcreator.data.Card;
import cardcreator.data.CardAndId;
import cardcreator.data.CardId;
import cardcreator.data.EditCard;
import cardcreator.data.ErrorMessageException;
import cardcreator.database.CardDatabase;
import cardcreator.database.postgres.PostgresCardDatabaseFactory;

import java.util.List;

public class Service
{
    private final CardDatabase database;

    public Service()
    {
        this.database = PostgresCardDatabaseFactory.create();
    }

    public List<CardAndId> listCards( String userId ) throws ErrorMessageException
    {
        List<CardAndId> cards = null;
        try
        {
            cards = database.listCards( userId );
        }
        catch( Exception e )
        {
            e.printStackTrace();//TODO logging
        }
        if( cards == null )
        {
            throw new ErrorMessageException( "Error retrieving card list." );
        }
        return cards;
    }

    public Card getCard( String userId, CardId cardId ) throws ErrorMessageException
    {
        try
        {
            Card card = database.getCard( userId, cardId.id() );
            if( card == null )
            {
                throw new ErrorMessageException( "Card not found." );
            }
            return card;
        }
        catch( ErrorMessageException e )
        {
            throw e;
        }
        catch( Exception e )
        {
            e.printStackTrace();//TODO logging
            throw new ErrorMessageException( "Error retrieving card." );

        }
    }

    public CardId newCard( String userId, Card card ) throws ErrorMessageException
    {
        String id = null;
        try
        {
            id = database.addNewCard( userId, card );
        }
        catch( Exception e )
        {
            e.printStackTrace();//TODO logging
        }
        if( id == null )
        {
            throw new ErrorMessageException( "Error creating card." );
        }
        return new CardId( id );
    }

    public void editCard( String userId, EditCard cardEdit ) throws ErrorMessageException
    {
        try
        {
            if( !database.editCard( userId, cardEdit.id(), cardEdit.oldCard(), cardEdit.newCard() ) )
            {
                throw new ErrorMessageException( "The card cannot be edited because it has been changed. Review the latest changes before editing." );
            }
        }
        catch( ErrorMessageException e )
        {
            throw e;
        }
        catch( Exception e )
        {
            e.printStackTrace();//TODO logging
            throw new ErrorMessageException( "Error editing card." );
        }
    }

    public void deleteCard( String userId, CardAndId cardAndId ) throws ErrorMessageException
    {
        try
        {
            if( !database.deleteCard( userId, cardAndId.id(), cardAndId.card() ) )
            {
                throw new ErrorMessageException( "The card cannot be deleted because it has been changed. Review the latest changes before deleting." );
            }
        }
        catch( ErrorMessageException e )
        {
            throw e;
        }
        catch( Exception e )
        {
            e.printStackTrace();//TODO logging
            throw new ErrorMessageException( "Error deleting card." );
        }
    }
}
