import { Routes } from '@angular/router';
import { EventosComponent } from './pages/eventos/eventos.component'; 

export const routes: Routes = [
  { path: '', redirectTo: 'eventos', pathMatch: 'full' },
  { path: 'eventos', component: EventosComponent, title: 'Eventos - NewWay' }
];