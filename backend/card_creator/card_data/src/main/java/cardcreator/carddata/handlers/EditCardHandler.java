package cardcreator.carddata.handlers;

import cardcreator.carddata.database.CardDatabase;
import cardcreator.carddata.data.Card;
import cardcreator.util.ErrorHandler;
import cardcreator.util.HandlerRequests;
import cardcreator.util.HandlerResponses;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class EditCardHandler extends ErrorHandler
{
    @Override
    protected APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context )
    {
        EditCard card = HandlerRequests.parse( request, EditCard.class );
        if( CardDatabase.DB.editCard( card.id, card.oldCard, card.newCard ) )
        {
            return HandlerResponses.ok();
        }
        return HandlerResponses.error( "The card cannot be edited because someone else has changed it. Review the latest changes before editing." );
    }

    private record EditCard( String id, Card oldCard, Card newCard )
    {
    }
}
