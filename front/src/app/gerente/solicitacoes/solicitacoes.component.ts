import { Component, OnInit } from '@angular/core';

export interface Cliente{
  id: number;
  nome: string;
  cpf: string;
  salario: number;
}

const matrizCliente: Cliente[] = [
  { id: 1, nome: 'Marcio Oliveira', cpf: '555.782.862-16', salario: 2350.00 },
  { id: 2, nome: 'Everaldo Costa', cpf: '220.221.419-46', salario: 1800.00 },
  { id: 3, nome: 'Joana Oliveira', cpf: '417.843.325-34', salario: 3560.00 },
  { id: 4, nome: 'Camila da Silva', cpf: '525.160.851-92', salario: 1500.00 },
  { id: 5, nome: 'Valter Valencio', cpf: '847.464.609-06', salario: 1670.00 },
  { id: 6, nome: 'Alexandre Pato dos Santos', cpf: '776.674.889-40', salario: 5491.00 },
  { id: 7, nome: 'Geraldo Gomes', cpf: '312.693.661-06', salario: 12000.00 },
  { id: 8, nome: 'Emerson Vieira', cpf: '614.553.173-81', salario: 6700.00 },
];
@Component({
  selector: 'app-solicitacoes',
  templateUrl: './solicitacoes.component.html',
  styleUrls: ['./solicitacoes.component.css']
})
export class SolicitacoesComponent implements OnInit {
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  colunas: string[] = ['nome', 'cpf', 'salario','acao'];
  dataSource = matrizCliente; 
  constructor() { }
  aceitar(nome: any) {
    confirm(`Deseja Aceitar ${nome}`); 
  }
  rejeitar(nome: any) {
    confirm(`Deseja rejeitar ${nome}`); 
  }
}
