import { Component, OnInit } from "@angular/core";
import IMask from "imask";
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

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {}

  salvarGerente() {
    if (!this.nome || !this.email || !this.cpf) {
      alert("Preencha todos os campos");
      return;
    }
    this.adminService.salvarGerente({
      nome: this.nome,
      email: this.email,
      cpf: this.cpf,
    });
  }
}
