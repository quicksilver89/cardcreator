package cardcreator.aws.util;

import cardcreator.data.ErrorMessageException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public abstract class ConsumerHandler<P> extends ErrorHandler
{
    private final Class<P> paramType;

    protected ConsumerHandler( Class<P> paramType )
    {
        this.paramType = paramType;
    }

    @Override
    protected APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context ) throws ErrorMessageException
    {
        accept( HandlerRequests.parse( request, paramType ) );
        return HandlerResponses.ok();
    }

    protected abstract void accept( P param ) throws ErrorMessageException;
}
