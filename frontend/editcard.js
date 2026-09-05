const id = new URLSearchParams( window.location.search ).get( 'id' );

let oldCard = null;

if( id )
{
    document.getElementById( 'loading' ).style.display = 'block';

    fetchService( 'getcard', { id: id } )
    .then( data =>
    {
        oldCard = data;
        document.getElementById( 'name' ).value = data.name || '';
        document.getElementById( 'cost' ).value = data.cost || '';
        document.getElementById( 'text' ).value = data.text || '';

        document.getElementById( 'loading' ).style.display = 'none';
        document.getElementById( 'contents' ).style.display = 'block';
    } )
    .catch( error =>
    {
        console.error( error );
        document.getElementById( 'loading' ).textContent = 'Failed to load data: ' + error.message;
    } );
}
else
{
    document.getElementById( 'contents' ).style.display = 'block';
}

document.getElementById( 'submitBtn' ).addEventListener( 'click', () =>
{
    let functionName;
    let payload;

    const card =
    {
        name: document.getElementById( 'name' ).value,
        cost: document.getElementById( 'cost' ).value,
        text: document.getElementById( 'text' ).value
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

    fetchService( functionName, payload )
    .then( _ => window.location.href = 'listcards.html' )
    .catch( error =>
    {
        console.error( error );
        alert( error.message );
    } );
} );