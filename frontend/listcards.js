window.addEventListener( 'DOMContentLoaded', async () =>
{
    fetchService( 'listcards' )
    .then( data =>
    {
        const table = document.getElementById( 'table-body' );
        data.forEach( cardAndId => addRow( table, cardAndId ) );
        document.getElementById( 'loading' ).style.display = 'none';
        document.getElementById( 'table' ).style.display = 'table';
    } )
    .catch( error =>
    {
        console.error( error );
        document.getElementById( 'loading' ).textContent = 'Failed to load data: ' + error.message;
    } );
} );

function addRow( table, cardAndId )
{
    const card = cardAndId.card;
    const row = table.insertRow();
    row.insertCell( 0 ).textContent = card.name;
    row.insertCell( 1 ).textContent = card.cost;
    row.insertCell( 2 ).textContent = card.text;

    const editButton = document.createElement( 'button' );
    editButton.textContent = 'Edit';
    editButton.addEventListener( 'click', ()=> editCard( cardAndId.id ) );

    const deleteButton = document.createElement( 'button' );
    deleteButton.textContent = 'Delete';
    deleteButton.addEventListener( 'click', ()=> deleteCard( row, cardAndId ) );

    const buttonCell = row.insertCell( 3 );
    buttonCell.appendChild( editButton );
    buttonCell.appendChild( deleteButton );
}

function editCard( id )
{
    window.location.href = `editcard.html?id=${encodeURIComponent( id )}`;
}

function deleteCard( row, cardAndId )
{
    fetchService( 'deletecard', cardAndId )
    .then( _ => row.remove() )
    .catch( error =>
    {
        console.error( error );
        alert( error.message );
    } );
}