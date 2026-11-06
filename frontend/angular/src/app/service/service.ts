import { Service } from '@angular/core';
import { inject } from '@angular/core';
import { HttpClient, httpResource, HttpResourceRef } from '@angular/common/http';
import { Observable, share } from 'rxjs';
import { Card } from './data/card';
import { CardAndId } from './data/cardandid';
import { CardId } from './data/cardid';

@Service()
export class CardService {
  private readonly PATH: string = '/service';
  private readonly http: HttpClient = inject( HttpClient );

  readonly cards: HttpResourceRef<CardAndId[]|undefined> = httpResource<CardAndId[]>( () => `${this.PATH}/listcards` );

  getCard( cardId: string ): Observable<Card> {
    const observer: Observable<Card> =  this.http.post<Card>( `${this.PATH}/getcard`, { id: cardId } ).pipe( share() );
    observer.subscribe( {
      error: () => alert( 'Failed to get card info' ),
    } )
    return observer;
  }

  newCard( card: Card ): Observable<CardId> {
    const observer: Observable<CardId> = this.http.post<CardId>( `${this.PATH}/newcard`, card ).pipe( share() );
    observer.subscribe( {
      next: cardId => {
        const cardAndId: CardAndId = {id:cardId.id, card:card};
        this.cards.update( cards => cards ? [...cards, cardAndId] : [cardAndId] );
      },
      error: () => alert( 'Failed to create new card' ),
    } )
    return observer;
  }

  editCard( cardId: string, origCard: Card, newCard: Card ): Observable<void>  {
    const observer: Observable<void> = this.http.post<void>( `${this.PATH}/editcard`, { id: cardId, oldCard: origCard, newCard: newCard } ).pipe( share() );
    observer.subscribe( {
      next: () => {
        const cardAndId: CardAndId = {id:cardId, card:newCard};
        this.cards.update( cards => cards ? cards.map( c => c.id === cardId ? {id:cardId, card:newCard} : c ) : cards );
      },
      error: () => alert( 'Failed to edit card' ),
    } )
    return observer;
  }

  deleteCard( card: CardAndId ): Observable<void> {
    const observer: Observable<void> = this.http.post<void>( `${this.PATH}/deletecard`, card ).pipe( share() );
    observer.subscribe( {
      next: () => this.cards.update( cards => cards ? cards.filter( c => c.id !== card.id ) : cards ),
      error: () => alert( 'Failed to delete card' ),
    } )
    return observer;
  }
}
