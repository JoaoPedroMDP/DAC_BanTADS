import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-client",
  templateUrl: "./client.component.html",
  styleUrls: ["./client.component.scss"],
})
export class ClientComponent implements OnInit {
  search = false;

  routerActive = "";
  sidebarMenu = [
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

  constructor() {}

  ngOnInit(): void {}
}
