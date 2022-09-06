import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AdminService } from "../services/admin.service";

@Component({
  selector: "app-gerente",
  templateUrl: "./gerente.component.html",
  styleUrls: ["./gerente.component.css"],
})
export class GerenteComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private adminService: AdminService
  ) {}

  gerente: any = null;

  ngOnInit(): void {
    const id: any = this.route.snapshot.paramMap.get("id");
    console.log(id);
    this.gerente = this.adminService
      .getGerenteById(id)
      ?.subscribe((response: any) => {
        this.gerente = response.data;
        console.log(this.gerente);
      });
  }

  deletarGerente(id: string) {
    this.adminService.deletarGerente(id);
  }
}
