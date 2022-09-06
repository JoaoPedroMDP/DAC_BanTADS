import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/auth/services/auth.service";

@Injectable({
  providedIn: "root",
})
export class ClienteService {
  private url = "http://localhost:50001";

  private cliente = {};

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {}

  public async loadCliente() {
    // const { user } = this.authService.getAuth();
    // await this.http
    //   .get(this.url + "/clientes/" + user)
    //   .subscribe((response) => {
    //     this.scliente = response;
    //   });
  }

  private validateRegister({
    nome,
    email,
    senha,
    cpf,
    endereço,
  }: {
    nome: string;
    email: string;
    senha: string;
    cpf: string;
    endereço: {
      rua: string;
      numero: string;
      bairro: string;
      cidade: string;
      estado: string;
      cep: string;
    };
  }) {
    if (!nome || !email || !senha || !cpf || !endereço) {
      return false;
    }
    return true;
  }

  public async registerCliente(data: any) {
    if (!this.validateRegister(data)) {
      return alert("Preencha todos os campos");
    }

    await this.http.post(this.url + "/clientes", data).subscribe((response) => {
      this.router.navigate(["/login"]);
    });
  }

  public getCliente() {
    return this.cliente;
  }
}
