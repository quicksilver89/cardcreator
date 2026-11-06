import { Component, inject, signal, WritableSignal } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { CardService } from "../service/service";
import { Card } from "../service/data/card";

@Component({
  imports: [ReactiveFormsModule],
  templateUrl: "./cardedit.html",
})
export class CardEdit {
  private readonly router = inject(Router);
  private readonly route: ActivatedRoute = inject(ActivatedRoute);
  private readonly cardService: CardService = inject(CardService);
  private readonly cardId: string = this.route.snapshot.params['cardId'];
  private origCard: Card|undefined;
  private readonly cardForm: FormGroup = new FormGroup({
    name: new FormControl( '' ),
    cost: new FormControl( '' ),
    text: new FormControl( '' )
  });
  private readonly saveButtonDisabled: WritableSignal<boolean> = signal(false);

  constructor() {
    if( this.cardId ) {
      this.saveButtonDisabled.update( v => true );
      this.cardService.getCard( this.cardId ).subscribe( {
        next: card =>
        {
          this.origCard = card;
          this.cardForm.patchValue( card );
          this.saveButtonDisabled.update( v => false );
        }
      } );
    }
  }

  private submitCard() {
    this.saveButtonDisabled.update( v => true );
    if( this.cardId ) {
      if( this.origCard ) {
        this.waitForResult( this.cardService.editCard( this.cardId, this.origCard, this.cardForm.value ) );
      }
    }
    else {
      this.waitForResult( this.cardService.newCard( this.cardForm.value ) );
    }
  }

  private waitForResult( observer: Observable<unknown> ) {
    observer.subscribe( {
      next: () => this.router.navigate( ['/listcards'] ),
      error: () => this.saveButtonDisabled.update( v => false )
    } );
  }
}
