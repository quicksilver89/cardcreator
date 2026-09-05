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

public class GetCardHandler extends ErrorHandler
{
    @Override
    protected APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context )
    {
        Card card = CardDatabase.DB.getCard( HandlerRequests.parse( request, CardId.class ).id() );
        if( card == null )
        {
            return HandlerResponses.error( "Card not found." );
        }
        return HandlerResponses.ok( card );
    }
}
