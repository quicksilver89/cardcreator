package cardcreator.carddata.handlers;

import cardcreator.carddata.database.CardDatabase;
import cardcreator.carddata.data.Card;
import cardcreator.util.ErrorHandler;
import cardcreator.util.HandlerRequests;
import cardcreator.util.HandlerResponses;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class DeleteCardHandler extends ErrorHandler
{
    @Override
    protected APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context )
    {
        DeleteCard card = HandlerRequests.parse( request, DeleteCard.class );
        if( CardDatabase.DB.deleteCard( card.id, card.card ) )
        {
            return HandlerResponses.ok();
        }
        return HandlerResponses.error( "The card cannot be deleted because someone else has changed it. Review the latest changes before deleting." );
    }

    private record DeleteCard( String id, Card card )
    {
    }
}
