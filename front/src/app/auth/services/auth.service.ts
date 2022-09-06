import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Auth } from "./types";

import { ToastrService } from "ngx-toastr";
@Injectable({
  providedIn: "root",
})
export class AuthService {
  private url = "http://localhost:5000/auth";
  private auth: Auth | null = null;

  constructor(
    private http: HttpClient,
    private router: Router,
    private toast: ToastrService
  ) {}

  public navigateToUserHome() {
    const userRole = this.auth?.role;

    switch (userRole) {
      case "ADMIN":
        return this.router.navigate(["/admin"]);
      case "GERENTE":
        return this.router.navigate(["/gerente"]);
      case "CLIENTE":
        return this.router.navigate(["/cliente"]);
      default:
        this.setAuth(null);
        return this.router.navigate(["/login"]);
    }
  }

  private setAuth(auth: Auth | null) {
    this.auth = auth;
    if (!auth) {
      return localStorage.removeItem("auth");
    }
    localStorage.setItem("auth", JSON.stringify(auth));
  }

  public async login(email: string, password: string) {
    return await this.http
      .post(this.url + "/login", { email, password })
      .subscribe(
        (response: any) => {
          //TODO: error handling
          if (response.success) {
            this.setAuth(response.data);
            this.navigateToUserHome();
            this.toast.success(
              "Login realizado com sucesso!",
              "Bem vindo ao BanTADS!"
            );
            return response;
          }
        },
        ({ error }) => {
          this.toast.error(error.message, "Erro ao realizar login");
        }
      );
  }

  public getAuth(): Auth | null {
    return this.auth;
  }

  public async initAuth() {
    const auth = await localStorage.getItem("auth");
    if (auth) {
      this.auth = JSON.parse(auth);
    }
    return this.auth;
  }

  public logOut() {
    this.setAuth(null);
    this.router.navigate(["/login"]);
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem("token"); // Check whether the token is expired and return
    // true or false
    return !!token;
    // return !this.jwtHelper.isTokenExpired(token || "");
  }
}
