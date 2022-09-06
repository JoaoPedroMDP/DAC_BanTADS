import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import IMask from "imask";
import { ToastrService } from "ngx-toastr";
import { ClienteService } from "../services/cliente.service";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"],
})
export class RegisterComponent implements OnInit {
  cpfMask = { mask: "000.000.000-00" };
  cepMask = { mask: "00000-000" };
  numberMask = { mask: IMask.MaskedNumber };

  form = new FormGroup({
    nome: new FormControl(""),
    cpf: new FormControl(""),
    email: new FormControl(""),
    cep: new FormControl(""),
    rua: new FormControl(""),
    numero: new FormControl(""),
    cidade: new FormControl(""),
    estado: new FormControl(""),
    complemento: new FormControl(""),
    password: new FormControl(""),
    confirmPassword: new FormControl(""),
    salario: new FormControl(""),
  });

  constructor(
    private clienteService: ClienteService,
    private toast: ToastrService
  ) {}

  ngOnInit(): void {}

  public handleRegister() {
    const { password, confirmPassword } = this.form.value;
    if (password !== confirmPassword) {
      return this.toast.error("As senhas não conferem!", "Erro ao cadastrar");
    }
    const clienteData = {
      ...this.form.value,
      endereco: {
        cep: this.form.value.cep,
        rua: this.form.value.rua,
        numero: this.form.value.numero,
        cidade: this.form.value.cidade,
        estado: this.form.value.estado,
        complemento: this.form.value.complemento,
      },
    };

    this.clienteService.registerCliente(clienteData);
    return;
  }
}
