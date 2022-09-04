import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  public email: string = "";
  public password: string = "";

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {}

  handleLogin() {
    const user = this.authService.login({
      email: this.email,
      password: "123456",
    });

    // if (this.email === "admin") {
    //   this.router.navigate(["/admin"]);
    //   return console.log("admin");
    // }
    // if (this.email === "gerente") {
    //   this.router.navigate(["/gerente"]);
    //   return console.log("gerente");
    // }
    // this.router.navigate(["/cliente"]);
    // return console.log("cliente");
  }
}
