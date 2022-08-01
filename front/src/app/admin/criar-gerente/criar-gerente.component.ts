import { Component, OnInit } from "@angular/core";
import IMask from "imask";

@Component({
  selector: "app-criar-gerente",
  templateUrl: "./criar-gerente.component.html",
  styleUrls: ["./criar-gerente.component.css"],
})
export class CriarGerenteComponent implements OnInit {
  cpfMask = { mask: "000.000.000-00" };
  constructor() {}

  ngOnInit(): void {}
}
