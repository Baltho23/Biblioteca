import { Routes } from '@angular/router';
import { Books } from './modules/modulos/books/books';
import { Dashboard } from './modules/dashboard/dashboard';
import { Loans } from './modules/modulos/loans/loans';
import { Members } from './modules/modulos/members/members';
import { Statistics } from './modules/modulos/statistics/statistics';

export const routes: Routes = [
  {
    path: 'dashboard',
    component: Dashboard,
    children: [
      { path: 'books', component: Books },
      { path: 'loans', component: Loans },
      { path: 'members', component: Members },
      { path: 'statistics', component: Statistics },
      { path: '', redirectTo: 'books', pathMatch: 'full' }
    ]
  },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];
