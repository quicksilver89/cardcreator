package cardcreator.carddata.handlers;

import cardcreator.carddata.data.Card;
import cardcreator.carddata.data.CardId;
import cardcreator.carddata.database.CardDatabase;
import cardcreator.util.ErrorHandler;
import cardcreator.util.HandlerRequests;
import cardcreator.util.HandlerResponses;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class NewCardHandler extends ErrorHandler
{
    @Override
    protected APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context )
    {
        String id = CardDatabase.DB.addNewCard( HandlerRequests.parse( request, Card.class ) );
        if( id == null )
        {
            return HandlerResponses.error( "Could not create new card." );
        }
        return HandlerResponses.ok( new CardId( id ) );
    }
}
