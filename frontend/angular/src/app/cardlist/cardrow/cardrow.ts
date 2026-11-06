import { Component, input, Output, EventEmitter } from "@angular/core";
import { RouterLink } from "@angular/router";
import { CardAndId } from "../../service/data/cardandid";

@Component({
  imports: [RouterLink],
  selector: "tr[app-cardlist-row]",
  templateUrl: "./cardrow.html",
})
export class CardRow {
  readonly card = input.required<CardAndId>();

  @Output() delete = new EventEmitter<void>();
}