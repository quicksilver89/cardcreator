import {fetchService} from './service.js';
import type {Card} from './types.js';

interface EditCardPayload
{
    id: string;
    oldCard: Card;
    newCard: Card;
}

const id = new URLSearchParams( window.location.search ).get( 'id' );

let oldCard: Card | null = null;

async function loadCard( cardId: string ): Promise<void>
{
    document.getElementById( 'loading' )!.style.display = 'block';

    try
    {
        const data = await fetchService<Card>( 'getcard', { id: cardId } ) as Card;
        oldCard = data;
        ( document.getElementById( 'name' ) as HTMLInputElement ).value = data.name || '';
        ( document.getElementById( 'cost' ) as HTMLInputElement ).value = data.cost || '';
        ( document.getElementById( 'text' ) as HTMLInputElement ).value = data.text || '';

        document.getElementById( 'loading' )!.style.display = 'none';
        document.getElementById( 'contents' )!.style.display = 'block';
    }
    catch( error )
    {
        console.error( error );
        document.getElementById( 'loading' )!.textContent = 'Failed to load data: ' + (error as Error).message;
    }
}

if( id )
{
    loadCard( id );
}
else
{
    document.getElementById( 'contents' )!.style.display = 'block';
}

document.getElementById( 'submitBtn' )!.addEventListener( 'click', async () =>
{
    let functionName: string;
    let payload: Card | EditCardPayload;

    const card: Card =
        {
            name: ( document.getElementById( 'name' ) as HTMLInputElement ).value,
            cost: ( document.getElementById( 'cost' ) as HTMLInputElement ).value,
            text: ( document.getElementById( 'text' ) as HTMLInputElement ).value
        };

    if( id )
    {
        if( !oldCard )
        {
            return;
        }

        functionName = 'editcard';
        payload =
        {
            id: id,
            oldCard: oldCard,
            newCard: card
        };
    }
    else
    {
        functionName = 'newcard';
        payload = card;
    }

    try
    {
        await fetchService( functionName, payload );
        window.location.href = 'listcards.html';
    }
    catch( error )
    {
        console.error( error );
        alert( ( error as Error ).message );
    }
} );