package cardcreator.aws.util;

import cardcreator.data.ErrorMessageException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public abstract class FunctionHandler<P> extends ErrorHandler
{
    private final Class<P> paramType;

    protected FunctionHandler( Class<P> paramType )
    {
        this.paramType = paramType;
    }

    @Override
    protected APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context ) throws ErrorMessageException
    {
        return HandlerResponses.ok( apply( HandlerRequests.parse( request, paramType ) ) );
    }

    protected abstract Object apply( P param ) throws ErrorMessageException;
}
