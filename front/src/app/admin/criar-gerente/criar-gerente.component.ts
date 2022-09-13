import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import IMask from "imask";
import { ToastrService } from "ngx-toastr";
import { AdminService } from "../services/admin.service";

@Component({
  selector: "app-criar-gerente",
  templateUrl: "./criar-gerente.component.html",
  styleUrls: ["./criar-gerente.component.css"],
})
export class CriarGerenteComponent implements OnInit {
  cpfMask = { mask: "000.000.000-00" };
  nome = "";
  email = "";
  cpf = "";

  storedGerente: any = null;

  isEditing = false;
  id = "";

  constructor(
    private adminService: AdminService,
    private toast: ToastrService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    if (!!this.route.snapshot.paramMap.get("id")) {
      this.isEditing = true;
      this.id = this.route.snapshot.paramMap.get("id") || "";
      this.adminService.getGerenteById(this.id)?.subscribe(({ data }: any) => {
        this.nome = data.nome;
        this.email = data.email;
        this.cpf = data.cpf;
        this.storedGerente = data;
      });
    }
  }

  salvarGerente() {
    if (!this.nome || !this.email || !this.cpf) {
      // alert("Preencha todos os campos");
      this.toast.error("Preencha todos os campos", "Erro ao cadastrar gerente");
      return;
    }
    if (this.isEditing) {
      this.adminService.editarGerente({
        ...this.storedGerente,
        nome: this.nome,
        email: this.email,
        cpf: this.cpf,
      });
      return;
    }
    this.adminService.salvarGerente({
      nome: this.nome,
      email: this.email,
      cpf: this.cpf,
    });
  }
}
