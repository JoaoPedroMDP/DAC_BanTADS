import { HttpClient } from "@angular/common/http";
import { Component, OnInit, ViewChild } from "@angular/core";
import { NgForm } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { ClienteService } from "src/app/cliente/services/cliente.service";

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

const matrizCliente: Cliente[] = [
  {
    id: 1,
    nome: "Alexandre Pato dos Santos",
    email: "alexandrepatodossantos@hotmail.com",
    cpf: "776.674.889-40",
    salario: 5491.0,
    tipo: "Rua",
    logradouro: "Rua Nossa Senhora de Fátima",
    numero: 73,
    complemento: "Casa 3",
    cep: "59090-647",
    cidade: "Natal",
    estado: "RN",
  },
  {
    id: 2,
    nome: "Camila da Silva",
    email: "camiladasilvaprog@rocketmail.com",
    cpf: "525.160.851-92",
    salario: 1500.0,
    tipo: "Via",
    logradouro: "Via Secundária 6",
    numero: 5040,
    complemento: "Apartamento 1503 ",
    cep: "75913-562",
    cidade: "Rio Verde",
    estado: "GO",
  },
  {
    id: 3,
    nome: "Emerson Vieira",
    email: "emersonvieira@gmail.com",
    cpf: "614.553.173-81",
    salario: 6700.0,
    tipo: "Rua",
    logradouro: "Rua da Paz",
    numero: 150,
    complemento: " Apartamento 701 ",
    cep: "69908-048",
    cidade: "Rio Branco",
    estado: "AC",
  },
  {
    id: 4,
    nome: "Everaldo Costa",
    email: "everaldoCosta21@gmail.com",
    cpf: "220.221.419-46",
    salario: 1800.0,
    tipo: "Rua",
    logradouro: "Rua Rio de Janeiro",
    numero: 28,
    complemento: "Apartamento 1023",
    cep: "93280-390",
    cidade: "Esteio",
    estado: "RS",
  },
  {
    id: 5,
    nome: "Geraldo Gomes",
    email: "GeraldoGomesgg@gmail.com",
    cpf: "312.693.661-06",
    salario: 12000.0,
    tipo: "Travessa",
    logradouro: "Travessa Sete de Setembro",
    numero: 731,
    complemento: "Fundos",
    cep: "66015-210",
    cidade: "Belém",
    estado: "PA",
  },
  {
    id: 6,
    nome: "Joana Oliveira",
    email: "joaninhaoliveira@yahoo.com",
    cpf: "417.843.325-34",
    salario: 3560.0,
    tipo: "Beco",
    logradouro: "Beco Jaguarari",
    numero: 760,
    complemento: "Apartamento 102",
    cep: "69036-604",
    cidade: "Manaus",
    estado: "AM",
  },
  {
    id: 7,
    nome: "Marcio Oliveira",
    email: "marcioliveira@gmail.com",
    cpf: "555.782.862-16",
    salario: 2350.0,
    tipo: "Rua",
    logradouro: "Rua Ivora",
    numero: 10,
    complemento: "Fundos",
    cep: "89237-530",
    cidade: "Joinville",
    estado: "SC",
  },
  {
    id: 8,
    nome: "Valter Valencio",
    email: "valenciovava@bol.com",
    cpf: "847.464.609-06",
    salario: 1670.0,
    tipo: "Rua",
    logradouro: "Rua do Alcides Luiz de Oliveira",
    numero: 3050,
    complemento: "Casa 1",
    cep: "55030-171",
    cidade: "Caruaru",
    estado: "PE",
  },
];
@Component({
  selector: "app-consulta-cliente",
  templateUrl: "./consulta-cliente.component.html",
  styleUrls: ["./consulta-cliente.component.css"],
})
export class ConsultaClienteComponent implements OnInit {
  @ViewChild("formCliente") formCliente!: NgForm;
  cliente!: any;
  conta!: any;
  // dataSource = matrizCliente;
  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private clienteService: ClienteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.http
      .get("http://localhost:3000/clientes/" + this.route.snapshot.params["id"])
      .subscribe((res: any) => {
        console.log(res);
        this.cliente = res.data;
      });
    this.http
      .get(
        "http://localhost:3000/accounts/user/" +
          this.route.snapshot.params["id"]
      )
      .subscribe((res: any) => {
        console.log(res);
        this.conta = res.data;
      });
  }
}
