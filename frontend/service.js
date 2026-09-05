function fetchService( serviceName, param )
{
    const url = '/service/' + serviceName;
    return ( (param === undefined ) ?
        fetch( url ) :
        fetch( url,
        {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify( param )
        } ) )
        .then( getJson );
}

function getJson( response )
{
    return response.text()
    .then( text =>
    {
        let data;
        try
        {
            data = JSON.parse( text );
        }
        catch( jsonError )
        {
            if( response.ok )
            {
                return text;
            }
            throw new Error( text );
        }

        if( response.ok )
        {
            return data;
        }

        if( data && typeof data === 'object' && 'error' in data )
        {
            throw new Error( data.error );
        }

        throw new Error( text );
    } );
}