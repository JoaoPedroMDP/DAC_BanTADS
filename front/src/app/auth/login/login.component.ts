import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  public email: string = "";

  constructor(private router: Router) {}

  ngOnInit(): void {}

  handleLogin() {
    if (this.email === "admin") {
      this.router.navigate(["/admin"]);
      return console.log("admin");
    }
    if (this.email === "gerente") {
      this.router.navigate(["/gerente"]);
      return console.log("gerente");
    }
    this.router.navigate(["/cliente"]);
    return console.log("cliente");
  }
}
