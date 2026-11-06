import { Routes } from '@angular/router';
import { CardList } from './cardlist/cardlist';
import { CardEdit } from './cardedit/cardedit';

export const routes: Routes = [
  {
    path: '',
    component: CardList,
    title: 'Cards',
  },
  {
    path: 'listcards',
    component: CardList,
    title: 'Cards',
  },
  {
    path: 'editcard',
    component: CardEdit,
    title: 'New Card',
  },
  {
    path: 'editcard/:cardId',
    component: CardEdit,
    title: 'Edit Card',
  }
];
