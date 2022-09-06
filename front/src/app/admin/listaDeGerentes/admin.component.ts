import { Component, OnInit } from "@angular/core";
import { AdminService } from "../services/admin.service";

export interface PeriodicElement {
  id: number;
  name: string;
  nClientes: string;
  saldoPositivo?: string;
  saldoNegativo?: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {
    id: 1,
    name: "Deep Javiya",
    nClientes: "20",
    saldoPositivo: "+4000",
    saldoNegativo: "-1000",
  },
  {
    id: 2,
    name: "Nirav Joshi",
    nClientes: "20",
    saldoPositivo: "+4000",
    saldoNegativo: "-1000",
  },
  {
    id: 3,
    name: "Sunil Joshi",
    nClientes: "20",
    saldoPositivo: "+4000",
    saldoNegativo: "-1000",
  },
  {
    id: 4,
    name: "Maruti Makwana",
    nClientes: "20",
    saldoPositivo: "+4000",
    saldoNegativo: "-1000",
  },
];

@Component({
  selector: "app-admin",
  templateUrl: "./admin.component.html",
  styleUrls: ["./admin.component.scss"],
})
export class AdminComponent implements OnInit {
  displayedColumns: string[] = [
    "assigned",
    "name",
    "priority",
    "budget",
    "acao",
  ];
  dataSource: any[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.loadGerentes().subscribe((response: any) => {
      this.dataSource = response.data;
      console.log(response);
      this.adminService.setGerentes(response.data);
    });
  }

  deletarGerente(id: string) {
    this.adminService.deletarGerente(id);
  }
}
