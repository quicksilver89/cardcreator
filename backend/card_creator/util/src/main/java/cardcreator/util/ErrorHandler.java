package cardcreator.util;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public abstract class ErrorHandler implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent>
{
    @Override
    public APIGatewayProxyResponseEvent handleRequest( APIGatewayProxyRequestEvent request, Context context )
    {
        try
        {
            return handle( request, context );
        }
        catch( Exception e )
        {
            return HandlerResponses.error( e );
        }
    }

    protected abstract APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context ) throws Exception;
}
