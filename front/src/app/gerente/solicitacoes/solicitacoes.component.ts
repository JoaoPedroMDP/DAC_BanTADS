import { Component, OnInit } from "@angular/core";
import { GerenteService } from "../gerente.service";

export interface Cliente {
  id: number;
  nome: string;
  cpf: string;
  salario: number;
}

@Component({
  selector: "app-solicitacoes",
  templateUrl: "./solicitacoes.component.html",
  styleUrls: ["./solicitacoes.component.css"],
})
export class SolicitacoesComponent implements OnInit {
  colunas: string[] = ["nome", "cpf", "salario", "acao"];
  dataSource: any = [];
  constructor(private gerenteService: GerenteService) {}
  ngOnInit(): void {
    this.dataSource = this.gerenteService.getUnaprroved();
  }

  aceitar(id: number) {
    this.gerenteService.aprovar(id);
  }
  rejeitar(nome: any) {
    confirm(`Deseja rejeitar ${nome}`);
  }
}
