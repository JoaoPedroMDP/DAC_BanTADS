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

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    private toast: ToastrService
  ) {}

  public async loadGerente() {
    const auth = await this.authService.getAuth();
    console.log(auth?.user);
    return this.http.get(this.url + "/gerente/" + auth?.user);
  }

  public async getClientes() {
    const auth = await this.authService.getAuth();
    await this.http
      .get(this.url + "/clientes?gerente=" + auth?.user + "&conta=true")
      .subscribe(
        (response: any) => {
          this.clientes = response.data.clientes;
          this.clientes = this.clientes.map((c: any) => {
            c.account = response.data.accounts.find(
              (a: any) => a.userId == c.id
            );
          });
          return this.clientes;
        },
        ({ error }) => {
          this.toast.error(error.message, "Erro ao buscar clientes");
        }
      );
  }

  public getCliente() {
    return this.clientes;
  }

  public setCliente(cliente: any) {
    this.clientes = cliente;
  }
}
