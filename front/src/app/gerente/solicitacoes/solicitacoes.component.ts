import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { ToastrService } from "ngx-toastr";
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
  constructor(
    private gerenteService: GerenteService,
    private http: HttpClient,
    private toast: ToastrService
  ) {}
  ngOnInit(): void {
    this.dataSource = this.gerenteService.getUnaprroved();
  }

  aceitar(id: number) {
    this.gerenteService.aprovar(id);
  }
  rejeitar(id: number) {
    this.http
      .get("http://localhost:3000/clientes/" + id)
      .subscribe((res: any) => {
        let cliente = res.data;
        cliente.aprovado = "RECUSADO";

        this.http
          .put("http://localhost:3000/clientes/" + id, cliente)
          .subscribe((res: any) => {
            this.toast.success("Cliente rejeitado com sucesso");
          });
      });
  }
}
