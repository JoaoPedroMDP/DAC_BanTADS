import { Component, OnInit } from "@angular/core";
import { AuthService } from "src/app/auth/services/auth.service";

@Component({
  selector: "app-admin",
  templateUrl: "./admin.component.html",
})
export class AdminSideBarComponent implements OnInit {
  search = false;

  routerActive = "";
  sidebarMenu = [
    {
      link: "/admin",
      icon: "list",
      label: "Lista de Gerentes",
    },
    {
      link: "/admin/gerente",
      icon: "user",
      label: "Cadastrar Gerente",
    },
  ];

  constructor(private authService: AuthService) {}

  ngOnInit(): void {}

  logout() {
    this.authService.logOut();
  }
}
