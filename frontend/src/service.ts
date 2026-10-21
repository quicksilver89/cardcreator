import { getAccessToken } from './auth.js';

export function disableButton( button: HTMLButtonElement ): void
{
    button.disabled = true;
}

export function fetchServiceFromButton<T = unknown>( button: HTMLButtonElement, serviceName: string, param?: unknown ): Promise<T | null>
{
    disableButton( button );
    return fetchService( serviceName, param );
}

export async function fetchService<T = unknown>( serviceName: string, param?: unknown ): Promise<T | null>
{
    const accessToken = await getAccessToken();
    if (!accessToken)
    {
        throw new Error( 'Could not authenticate.' );
    }

    const url = '/service/' + serviceName;
    return ( ( param === undefined ) ?
        fetch( url,
        {
            method: 'GET',
            headers: { 'Authorization': `Bearer ${accessToken}` },
        } ) :
        fetch( url,
        {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${accessToken}`, 'Content-Type': 'application/json' },
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
        }

        if( response.ok )
        {
            return data as T;
        }

        if( data && typeof data === 'object' && 'message' in data )
        {
            throw new Error( ( data as { message: string } ).message );
        }

        throw new Error( "Error fetching data" );
    } );
}
