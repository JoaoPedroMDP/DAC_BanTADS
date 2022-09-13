import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/auth/services/auth.service";
import { ClienteService } from "../services/cliente.service";

@Component({
  selector: "app-client",
  templateUrl: "./client.component.html",
  styleUrls: ["./client.component.scss"],
})
export class ClientComponent implements OnInit {
  public cliente: any = null;
  search = false;
  public account: any = null;

  routerActive = "";
  sidebarMenu = [
    {
      link: "/cliente",
      icon: "home",
      label: "Home",
    },
    {
      link: "/cliente/transacoes/sacar",
      icon: "log-out",
      label: "Sacar",
    },
    {
      link: "/cliente/transacoes/depositar",
      icon: "log-in",
      label: "Depositar",
    },
    {
      link: "/cliente/transacoes/extrato",
      icon: "dollar-sign",
      label: "Extrato",
    },
    {
      link: "/cliente/transacoes/transferir",
      icon: "repeat",
      label: "Transferências",
    },
  ];

  constructor(
    private authService: AuthService,
    private router: Router,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.cliente = this.clienteService.getCliente();
      this.account = this.clienteService.getAccount();
    }, 500);
    // this.clienteService.loadAccount().then((res) => {
    //   res.subscribe((value: any) => {
    //     this.clienteService.setAccount(value.data);
    //     this.account = value.data;
    //   });
    // });
  }

  public handleLogout() {
    this.authService.logOut();
  }
}
