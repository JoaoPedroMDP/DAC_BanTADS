import { Component, OnInit } from "@angular/core";
import { AuthService } from "src/app/auth/services/auth.service";
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

  nome = "";
  cpf = "";

  constructor(
    private gerenteService: GerenteService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.initAuth();
    setTimeout(() => {
      this.gerenteService.loadGerente();
    }, 300);
    setTimeout(() => {
      this.gerenteService.getClientes().then((clientes: any) => {
        this.dataSource = clientes;
      });
    }, 700);

    setTimeout(() => {
      this.dataSource = this.gerenteService.getClientesObj();
      console.log(this.dataSource);
    }, 1200);
  }

  pesquisar() {
    if (this.cpf !== "") {
      return (this.dataSource = this.gerenteService.filtrarPorCpf(this.cpf));
    }
    if (this.nome !== "") {
      return (this.dataSource = this.gerenteService.filtrarPorNome(this.nome));
    }
  }

  top5() {
    return (this.dataSource = this.gerenteService.getTop5());
  }
}
