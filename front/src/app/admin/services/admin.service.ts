import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";

@Injectable({
  providedIn: "root",
})
export class AdminService {
  private gerenteUrl = "http://localhost:3000/gerente";
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
        this.gerentes.push(response.data);
        this.toast.success("Gerente cadastrado com sucesso!", "Sucesso!");
      },
      ({ error }) => {
        this.toast.error(error.message, "Erro ao cadastrar gerente");
      }
    );
  }

  public editarGerente(gerente: any) {
    this.http.put(this.gerenteUrl + "/" + gerente.id, gerente).subscribe(
      (response: any) => {
        this.gerentes.push(response.data);
        this.toast.success("Gerente editado com sucesso!", "Sucesso!");
      },
      ({ error }) => {
        console.error(error);
        this.toast.error(error.message, "Erro ao editar gerente");
      }
    );
  }

  public deletarGerente(id: string) {
    this.http.delete(this.gerenteUrl + "/" + id).subscribe(
      (response: any) => {
        this.gerentes.filter((g) => g.id !== id);
        this.toast.success("Gerente excluido com sucesso!", "Sucesso!");
      },
      ({ error }) => {
        console.error(error);
        this.toast.error(error.message, "Erro ao excluir gerente");
      }
    );
  }

  public getGerenteById(id: any) {
    if (!id) return;
    return this.http.get(this.gerenteUrl + "/" + id);
  }
}
