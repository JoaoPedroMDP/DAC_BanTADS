import { Component, OnInit } from "@angular/core";
import { GerenteService } from "../gerente.service";
export interface Cliente {
  id: number;
  nome: string;
  email: string;
  cpf: string;
  salario: number;
  tipo: string;
  logradouro: string;
  numero: number;
  complemento: string;
  cep: string;
  cidade: string;
  estado: string;
}

const matrizCliente: any[] = [];

@Component({
  selector: "app-consulta-clientes",
  templateUrl: "./consulta-clientes.component.html",
  styleUrls: ["./consulta-clientes.component.scss"],
})
export class ConsultaClientesComponent implements OnInit {
  colunas: string[] = ["nome", "cpf", "cidade", "estado", "saldo"];
  dataSource = matrizCliente;
  cpfMask = { mask: "000.000.000-00" };

  constructor(private gerenteService: GerenteService) {}

  ngOnInit(): void {
    this.gerenteService.getClientes().then((clientes: any) => {
      this.dataSource = clientes;
    });
  }
}
