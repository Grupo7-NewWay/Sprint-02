import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Evento } from '../../models/evento.model';

@Component({
  selector: 'app-eventos',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.css']
})

export class EventosComponent implements OnInit {

  private readonly API = 'http://3.219.123.208';

  listaEventos: Evento[] = [];

  eventoForm: Evento = this.inicializarFormulario();

  modoEdicao = false;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute
  ) { }

  idAgencia!: number;

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      this.idAgencia = Number(params['idAgencia']);
      console.log('ID Agencia:', this.idAgencia);
    });

    this.carregarEventos();
  }

  inicializarFormulario(): Evento {
    return { nome: '', localidade: '', data: '' };
  }

  carregarEventos(): void {

    this.http.get<Evento[]>(`${this.API}/eventos/carregarEventos`)
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

    if (!this.eventoForm.nome || !this.eventoForm.localidade || !this.eventoForm.data) {
      alert('Preencha todos os campos antes de enviar!');
      return;
    }

    if (this.modoEdicao) {

      this.http.put(
        `${this.API}/eventos/atualizarEvento/${this.eventoForm.id}`,
        {
          nomeServer: this.eventoForm.nome,
          localidadeServer: this.eventoForm.localidade,
          dataServer: this.eventoForm.data
        }
      ).subscribe({
        next: () => {
          this.cancelarEdicao();
          alert('Evento atualizado com sucesso!');
          this.carregarEventos();
        },
        error: (erro) => {
          console.error(erro);
        }
      });

    } else {

      this.http.post(
        `${this.API}/eventos/publicarEvento`,
        {
          nomeServer: this.eventoForm.nome,
          localidadeServer: this.eventoForm.localidade,
          dataServer: this.eventoForm.data,
          fkAgenciaServer: this.idAgencia
        }
      ).subscribe({
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
    this.eventoForm = {
      ...evento,
      data: evento.data ? evento.data.split('T')[0] : ''
    };
    console.log(this.eventoForm);
  }

  deletarEvento(id?: number): void {

    if (id === undefined) {
      console.error("ID undefined no delete");
      return;
    }

    if (confirm('Tem certeza que deseja excluir esse evento?')) {

      this.http.delete(`${this.API}/eventos/deletarEvento/${id}`)
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