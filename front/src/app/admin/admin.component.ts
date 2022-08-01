import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-admin",
  templateUrl: "./admin.component.html",
  styleUrls: ["./admin.component.css"],
})
export class AdminComponent implements OnInit {
  search = false;

  routerActive = "";
  sidebarMenu = [
    {
      link: "/admin",
      icon: "log-out",
      label: "Gerentes",
    },
    {
      link: "/admin/gerente",
      icon: "log-in",
      label: "Cadastrar gerente",
    },
  ];

  constructor() {}

  ngOnInit(): void {}
}
