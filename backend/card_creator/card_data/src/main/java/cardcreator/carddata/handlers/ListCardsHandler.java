package cardcreator.carddata.handlers;

import cardcreator.carddata.database.CardDatabase;
import cardcreator.carddata.data.CardAndId;
import cardcreator.util.ErrorHandler;
import cardcreator.util.HandlerResponses;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.List;

public class ListCardsHandler extends ErrorHandler
{
    @Override
    protected APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context )
    {
        List<CardAndId> cards = CardDatabase.DB.listCards();
        if( cards == null )
        {
            return HandlerResponses.error( "Error retrieving card list." );
        }
        return HandlerResponses.ok( cards );
    }
}
