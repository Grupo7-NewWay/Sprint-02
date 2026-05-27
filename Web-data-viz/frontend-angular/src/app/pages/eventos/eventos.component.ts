import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Evento } from '../../models/evento.model';
import { HttpClient } from '@angular/common/http';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-eventos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.css']
})



export class EventosComponent implements OnInit {

  listaEventos: Evento[] = [];

  eventoForm: Evento = this.inicializarFormulario();

  modoEdicao = false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.carregarEventos();
  }

  inicializarFormulario(): Evento {
    return { nome: '', localidade: '', data: '' };
  }

  carregarEventos(): void {

    this.http.get<Evento[]>(
      'http://localhost:8080/eventos/carregarEventos'
    )
      .subscribe({

        next: (resposta) => {

          console.log("EVENTOS DO BANCO:");
          console.log(resposta);

          this.listaEventos = resposta;
        },

        error: (erro) => {
          console.error(erro);
        }

      });

  }

  salvarEvento(): void {

    if (
      !this.eventoForm.nome ||
      !this.eventoForm.localidade ||
      !this.eventoForm.data
    ) {

      alert('Preencha todos os campos antes de enviar!');
      return;
    }

    if (this.modoEdicao) {

      this.http.put(
        `http://localhost:8080/eventos/atualizarEvento/${this.eventoForm.id}`,
        {
          nomeServer: this.eventoForm.nome,
          localidadeServer: this.eventoForm.localidade,
          dataServer: this.eventoForm.data
        }
      )
        .subscribe({

          next: () => {


            this.cancelarEdicao();

            alert('Evento atualizado com sucesso!');

            this.carregarEventos();

          },

          error: (erro) => {
            console.error(erro);
          }

        });

    }

    else {

      this.http.post(
        'http://localhost:8080/eventos/publicarEvento',
        {
          nomeServer: this.eventoForm.nome,
          localidadeServer: this.eventoForm.localidade,
          dataServer: this.eventoForm.data
        }
      )
        .subscribe({

          next: () => {

            alert('Evento criado com sucesso!');

            this.cancelarEdicao();
            this.carregarEventos();

            this.eventoForm = this.inicializarFormulario();

          },

          error: (erro) => {
            console.error(erro);
          }

        });

    }
  }

  prepararEdicao(evento: Evento): void {
    this.modoEdicao = true;
    this.eventoForm = { ...evento };
  }

  deletarEvento(id?: number): void {

  if (id === undefined) {
    console.error("ID undefined no delete");
    return;
  }

  if (confirm('Tem certeza que deseja excluir esse evento?')) {

    this.http.delete(
      `http://localhost:8080/eventos/deletarEvento/${id}`
    )
    .subscribe({

      next: () => {

        alert('Evento deletado com sucesso!');

        this.carregarEventos();

        if (this.eventoForm.id === id) {
          this.cancelarEdicao();
        }
      },

      error: (erro) => {
        console.error(erro);
      }

    });

  }
}

  cancelarEdicao(): void {
    this.modoEdicao = false;
    this.eventoForm = this.inicializarFormulario();
  }
}