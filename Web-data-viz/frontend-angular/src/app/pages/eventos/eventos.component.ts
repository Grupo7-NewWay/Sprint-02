import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Evento } from '../../models/evento.model';

@Component({
  selector: 'app-eventos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.css']
})
export class EventosComponent {
  
  listaEventos: Evento[] = [
    { id: 1, nome: 'Carnaval', localidade: 'Salvador, BA', data: '2026-03-01' },
    { id: 2, nome: 'Festa Junina', localidade: 'Campina Grande, PB', data: '2026-06-12' },
    { id: 3, nome: 'Oktoberfest', localidade: 'Blumenau, SC', data: '2026-10-18' }
  ];

  eventoForm: Evento = this.inicializarFormulario();

  modoEdicao = false;

  inicializarFormulario(): Evento {
    return { nome: '', localidade: '', data: '' };
  }

  salvarEvento(): void {
    if (!this.eventoForm.nome || !this.eventoForm.localidade || !this.eventoForm.data) {
      alert('Preencha todos os campos antes de enviar!');
      return;
    }

    if (this.modoEdicao) {

      const index = this.listaEventos.findIndex(e => e.id === this.eventoForm.id);
      if (index !== -1) {
        this.listaEventos[index] = { ...this.eventoForm };
      }
      this.modoEdicao = false;
    } else {
      const novoId = this.listaEventos.length > 0 
        ? Math.max(...this.listaEventos.map(e => e.id || 0)) + 1 
        : 1;
      
      this.listaEventos.push({ ...this.eventoForm, id: novoId });
    }

    this.eventoForm = this.inicializarFormulario();
  }

  prepararEdicao(evento: Evento): void {
    this.modoEdicao = true;
    this.eventoForm = { ...evento }; 
  }

  deletarEvento(id?: number): void {
    if (confirm('Tem certeza que deseja excluir esse evento?')) {
      this.listaEventos = this.listaEventos.filter(e => e.id !== id);
      
      if (this.eventoForm.id === id) {
        this.cancelarEdicao();
      }
    }
  }

  cancelarEdicao(): void {
    this.modoEdicao = false;
    this.eventoForm = this.inicializarFormulario();
  }
}