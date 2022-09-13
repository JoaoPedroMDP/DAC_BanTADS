import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { AuthService } from "src/app/auth/services/auth.service";

@Injectable({
  providedIn: "root",
})
export class ClienteService {
  private url = "http://localhost:3000";

  private cliente = {};

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    private toast: ToastrService
  ) {}

  public async loadCliente() {
    const auth = await this.authService.getAuth();
    console.log(auth?.user);
    return this.http.get(this.url + "/clientes/" + auth?.user);
  }

  public async registerCliente(data: any) {
    await this.http.post(this.url + "/clientes", data).subscribe(
      (response) => {
        this.toast.success(
          "Espere a aprovação da sua conta, e faça login!",
          "Cadastro realizado com sucesso!"
        );
        this.router.navigate(["/login"]);
      },
      ({ error }) => {
        this.toast.error(error.message, "Erro ao cadastrar cliente");
      }
    );
  }

  public getCliente() {
    return this.cliente;
  }

  public setCliente(cliente: any) {
    this.cliente = cliente;
  }
}
