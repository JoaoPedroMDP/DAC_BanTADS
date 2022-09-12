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

  ngOnInit(): void {
    this.authService.initAuth();
  }

  async handleLogin() {
    this.authService.login(this.email, this.password);
  }
}
