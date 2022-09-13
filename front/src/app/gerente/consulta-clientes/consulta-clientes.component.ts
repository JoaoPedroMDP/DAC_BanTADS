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
  top5Clientes: any = [];
  filtrando: boolean = false;

  // nome = "";
  // cpf = "";

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

  pesquisar(nome: string) {
    console.log(nome);
    if (nome !== "") {
      let aux: any = [];
      this.gerenteService.getClientesObj().forEach((cliente: any) => {
        if (cliente.nome.toLowerCase().includes(nome.toLowerCase())) {
          aux.push(cliente);
        }
      });
      console.log(aux);
      return (this.dataSource = aux);
    }

    this.dataSource = this.gerenteService.getClientesObj();
  }

  top5() {
    let aux = this.gerenteService
      .getClientesObj()
      .sort(
        (a: any, b: any) =>
          (b?.conta?.balance || -99999999) - (a?.conta?.balance || -9999999)
      )
      .splice(0, 5);
    return (this.dataSource = aux);
  }
}
