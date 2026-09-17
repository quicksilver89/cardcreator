package cardcreator.service;

import cardcreator.data.Card;
import cardcreator.data.CardAndId;
import cardcreator.data.CardId;
import cardcreator.data.EditCard;
import cardcreator.data.ErrorMessageException;
import cardcreator.database.CardDatabase;
import cardcreator.database.dynamodb.DynamoDbCardDatabase;

import java.util.List;

public class Service
{
    private final CardDatabase database;

    public Service()
    {
        this.database = new DynamoDbCardDatabase();
    }

    public List<CardAndId> listCards() throws ErrorMessageException
    {
        List<CardAndId> cards = database.listCards();
        if( cards == null )
        {
            throw new ErrorMessageException( "Error retrieving card list." );
        }
        return cards;
    }

    public Card getCard( CardId cardId ) throws ErrorMessageException
    {
        Card card = database.getCard( cardId.id() );
        if( card == null )
        {
            throw new ErrorMessageException( "Card not found." );
        }
        return card;
    }

    public CardId newCard( Card card ) throws ErrorMessageException
    {
        String id = database.addNewCard( card );
        if( id == null )
        {
            throw new ErrorMessageException( "Could not create new card." );
        }
        return new CardId( id );
    }

    public void editCard( EditCard cardEdit ) throws ErrorMessageException
    {
        if( !database.editCard( cardEdit.id(), cardEdit.oldCard(), cardEdit.newCard() ) )
        {
            throw new ErrorMessageException( "The card cannot be edited because someone else has changed it. Review the latest changes before editing." );
        }
    }

    public void deleteCard( CardAndId cardAndId ) throws ErrorMessageException
    {
        if( !database.deleteCard( cardAndId.id(), cardAndId.card() ) )
        {
            throw new ErrorMessageException( "The card cannot be deleted because someone else has changed it. Review the latest changes before deleting." );
        }
    }
}
