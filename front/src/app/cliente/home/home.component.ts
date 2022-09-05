import { Component, OnInit } from "@angular/core";
import { ClienteService } from "../services/cliente.service";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
})
export class HomeComponent implements OnInit {
  constructor(private clienteService: ClienteService) {}

  handleRaiseLimit() {
    alert("Não :)");
  }

  ngOnInit(): void {
    this.clienteService.loadCliente();
  }
}
