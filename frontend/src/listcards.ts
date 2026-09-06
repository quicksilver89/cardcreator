import {fetchService} from './service.js';
import type {CardAndId} from './types.js';

window.addEventListener( 'DOMContentLoaded', async () =>
{
    try
    {
        const data = await fetchService<CardAndId[]>( 'listcards' ) as CardAndId[];
        const table = document.getElementById( 'table-body' ) as HTMLTableElement;
        data.forEach( cardAndId => addRow( table, cardAndId ) );
        document.getElementById( 'loading' )!.style.display = 'none';
        document.getElementById( 'table' )!.style.display = 'table';
    }
    catch( error )
    {
        console.error( error );
        document.getElementById( 'loading' )!.textContent = 'Failed to load data: ' + ( error as Error ).message;
    }
} );

function addRow( table: HTMLTableElement, cardAndId: CardAndId ): void
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

function editCard( id: string ): void
{
    window.location.href = `editcard.html?id=${encodeURIComponent( id )}`;
}

async function deleteCard( row: HTMLTableRowElement, cardAndId: CardAndId ): Promise<void>
{
    try
    {
        await fetchService( 'deletecard', cardAndId );
        row.remove();
    }
    catch( error )
    {
        console.error( error );
        alert( ( error as Error ).message );
    }
}