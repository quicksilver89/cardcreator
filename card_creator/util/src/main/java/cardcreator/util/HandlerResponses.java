package cardcreator.util;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HandlerResponses
{
    public static APIGatewayProxyResponseEvent ok()
    {
        return ok( null );
    }

    public static APIGatewayProxyResponseEvent ok( Object output )
    {
        return createResponse( 200, output );
    }

    public static APIGatewayProxyResponseEvent error( Exception error )
    {
        return error( error.getMessage() );
    }

    public static APIGatewayProxyResponseEvent error( String message )
    {
        return createResponse( 500, Collections.singletonMap( "error", message ) );
    }

    private static APIGatewayProxyResponseEvent createResponse( int code, Object output )
    {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode( code );

        if( output != null )
        {
            Map<String, String> headers = new HashMap<>();
            headers.put( "Content-Type", "application/json" );
            response.setHeaders( headers );
            response.setBody( new Gson().toJson( output ) );
        }

        return response;
    }
}
