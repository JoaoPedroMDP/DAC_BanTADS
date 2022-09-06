import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";

@Injectable({
  providedIn: "root",
})
export class AdminService {
  private gerenteUrl = "http://localhost:5002/gerente";
  private gerentes: any[] = [];

  constructor(private http: HttpClient, private toast: ToastrService) {}

  public loadGerentes() {
    return this.http.get(this.gerenteUrl);
  }

  public getGerentes() {
    return this.gerentes;
  }

  public setGerentes(gerentes: any[]) {
    this.gerentes = gerentes;
  }

  public salvarGerente(gerente: any) {
    this.http.post(this.gerenteUrl, gerente).subscribe(
      (response: any) => {
        this.gerentes.push(response);
      },
      ({ error }) => {
        this.toast.error(error.message, "Erro ao cadastrar gerente");
      }
    );
  }

  public getGerenteById(id: number) {
    return this.http.get(this.gerenteUrl + "/" + id);
  }
}
