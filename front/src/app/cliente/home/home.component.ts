import { Component, OnInit } from "@angular/core";
import { AuthService } from "src/app/auth/services/auth.service";
import { ClienteService } from "../services/cliente.service";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
})
export class HomeComponent implements OnInit {
  constructor(
    private clienteService: ClienteService,
    private authService: AuthService
  ) {}

  public cliente: any = null;
  public account: any = null;

  handleRaiseLimit() {
    alert("Não :)");
  }

  ngOnInit(): void {
    this.authService.initAuth().then(() => {
      this.clienteService.loadCliente().then((res) => {
        res.subscribe((value: any) => {
          this.clienteService.setCliente(value.data);
          this.cliente = value.data;
        });
      });
      this.clienteService.loadAccount().then((res) => {
        res.subscribe((value: any) => {
          this.clienteService.setAccount(value.data);
          this.account = value.data;
        });
      });
    });
  }
}
