import { Component, OnInit } from "@angular/core";
import { BreakpointObserver, Breakpoints } from "@angular/cdk/layout";
import { Observable } from "rxjs";
import { map, shareReplay } from "rxjs/operators";
import { GerenteService } from "../gerente.service";
import { AuthService } from "src/app/auth/services/auth.service";

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
    {
      link: "/gerente/consultar-cliente",
      icon: "search",
      label: "Pesquisar Cliente por CPF",
    },
  ];

  gerente: any = {};

  constructor(
    private gerenteService: GerenteService,
    private authService: AuthService
  ) {}
  ngOnInit(): void {
    // this.gerenteService.loadGerente()
    setTimeout(() => {
      this.gerente = this.gerenteService.getGerente();
    }, 500);
  }

  logout() {
    this.authService.logOut();
  }
}
