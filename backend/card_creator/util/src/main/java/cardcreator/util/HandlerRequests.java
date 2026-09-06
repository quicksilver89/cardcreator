package cardcreator.util;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;

public class HandlerRequests
{
    private static final Gson GSON = new Gson();

    public static <T> T parse( APIGatewayProxyRequestEvent request, Class<T> type )
    {
        return GSON.fromJson( request.getBody(), type );
    }
}
