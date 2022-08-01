import { Component, OnInit } from "@angular/core";
import { BreakpointObserver, Breakpoints } from "@angular/cdk/layout";
import { Observable } from "rxjs";
import { map, shareReplay } from "rxjs/operators";

interface sidebarMenu {
  link: string;
  icon: string;
  menu: string;
}

@Component({
  selector: "app-full",
  templateUrl: "./full.component.html",
})
export class FullComponent implements OnInit {
  search = false;

  routerActive = "";
  sidebarMenu = [
    {
      link: "/gerente/consulta-clientes",
      icon: "log-out",
      label: "Consultar Clientes",
    },
    {
      link: "/gerente/solicitacoes",
      icon: "repeat",
      label: "Novos Clientes",
    },
  ];

  constructor() {}
  ngOnInit(): void {}
}
