package cardcreator.aws.util;

import cardcreator.data.ErrorMessageException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public abstract class SupplierHandler extends ErrorHandler
{
    @Override
    protected APIGatewayProxyResponseEvent handle( APIGatewayProxyRequestEvent request, Context context ) throws ErrorMessageException
    {
        return HandlerResponses.ok( get() );
    }

    protected abstract Object get() throws ErrorMessageException;
}
