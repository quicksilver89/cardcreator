export function fetchService<T = unknown>( serviceName: string, param?: unknown ): Promise<T | null>
{
    const url = '/service/' + serviceName;
    return ( ( param === undefined ) ?
        fetch( url ) :
        fetch( url,
        {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify( param )
        } ) )
        .then( getJson<T> );
}

function getJson<T = unknown>( response: Response ): Promise<T | null>
{
    return response.text()
    .then( text =>
    {
        let data: unknown;
        try
        {
            data = JSON.parse( text );
        }
        catch( jsonError )
        {
            if( response.ok )
            {
                return null;
            }
            throw new Error( text );
        }

        if( response.ok )
        {
            return data as T;
        }

        if( data && typeof data === 'object' && 'error' in data )
        {
            throw new Error( ( data as { error: string } ).error );
        }

        throw new Error( text );
    } );
}