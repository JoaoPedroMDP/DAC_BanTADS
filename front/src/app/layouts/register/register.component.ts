import { Component, OnInit } from "@angular/core";
import IMask from "imask";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"],
})
export class RegisterComponent implements OnInit {
  cpfMask = { mask: "000.000.000-75" };
  cepMask = { mask: "00000-000" };
  numberMask = { mask: IMask.MaskedNumber };

  constructor() {}

  ngOnInit(): void {}
}
