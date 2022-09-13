import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { AuthService } from "src/app/auth/services/auth.service";

@Injectable({
  providedIn: "root",
})
export class GerenteService {
  private url = "http://localhost:3000";

  private clientes: any = [];

  private clientesFiltered: any = [];
  private gerente: any = {};

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    private toast: ToastrService
  ) {}

  public async loadGerente() {
    const auth = this.authService.getAuth();
    return this.http
      .get(this.url + "/gerente/" + auth?.email)
      .subscribe((res: any) => {
        this.gerente = res.data;
      });
  }

  public getGerente() {
    return this.gerente;
  }

  public async getClientes() {
    console.log(this.gerente);
    await this.http
      .get(
        "http://localhost:3000/clientes?gerente=" +
          this.gerente.id +
          "&conta=true"
      )
      .subscribe(
        (response: any) => {
          let clientes = response.data.clientes;
          clientes.map((c: any) => {
            c.conta = response.data.accounts.find(
              (a: any) => a.userId === c.id
            );
          });
          console.log(clientes);
          this.clientes = clientes;
          return this.clientes;
        },
        ({ error }) => {
          this.toast.error(error.message, "Erro ao buscar clientes");
        }
      );
  }

  public getClientesObj() {
    return this.clientes;
  }

  public filtrarPorNome(nome: string) {
    return this.clientes.filter((cliente: any) => {
      return cliente.nome.toLowerCase().includes(nome.toLowerCase());
    });
  }

  public filtrarPorCpf(cpf: string) {
    return this.clientes.filter((cliente: any) => {
      return cliente.cpf === cpf;
    });
  }

  public getTop5() {
    return this.clientes
      .filter((c: any) => c?.account?.balance)
      .sort((a: any, b: any) => {
        a?.account.saldo - b.account.saldo;
      })
      .slice(0, 5);
  }

  public getUnaprroved() {
    console.log(this.clientes.filter((c: any) => c?.aprovado === "ANALISE"));
    return this.clientes.filter((c: any) => c?.aprovado === "ANALISE");
  }

  public getCliente() {
    return this.clientes;
  }

  public setCliente(cliente: any) {
    this.clientes = cliente;
  }

  public aprovar(id: number) {
    this.http
      .post(this.url + "/accounts", {
        userId: id,
        balance: 1000.0,
        limit: 0.0,
        transactions: null,
      })
      .subscribe(() => {
        this.toast.success("Cliente aprovado com sucesso", "Sucesso");
      });
  }
}
