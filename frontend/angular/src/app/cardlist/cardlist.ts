import { Component, inject } from "@angular/core";
import { RouterLink } from '@angular/router';
import { CardService } from "../service/service";
import { CardAndId } from "../service/data/cardandid";
import { CardRow } from "./cardrow/cardrow";

@Component({
  imports: [CardRow, RouterLink],
  templateUrl: "./cardlist.html",
})
export class CardList {
  private readonly cardService: CardService = inject(CardService);
}
