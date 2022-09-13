import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Auth } from "./types";

import { ToastrService } from "ngx-toastr";
import jwtDecode from "jwt-decode";
@Injectable({
  providedIn: "root",
})
export class AuthService {
  private url = "http://localhost:3000/auth";
  private auth: Auth | null = null;
  private role = "";

  constructor(
    private http: HttpClient,
    private router: Router,
    private toast: ToastrService
  ) {}

  public navigateToUserHome(userRole: string) {
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
    this.role = auth?.role || "";
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
            this.navigateToUserHome(response.data.role);
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
    console.log(this.auth);
    return this.auth;
  }

  public async initAuth() {
    const auth = await localStorage.getItem("auth");
    if (auth) {
      this.auth = JSON.parse(auth);
    }
    this.navigateToUserHome(this.auth?.role || "");
    return this.auth;
  }

  public logOut() {
    console.log("LOGGIN OUT");
    this.setAuth(null);
    localStorage.removeItem("auth");
    this.router.navigate(["/login"]);
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem("auth"); // Check whether the token is expired and return
    // if (!token) return false;
    // try {
    //   const decoded: any = jwtDecode(token);

    //   console.log(decoded.role);
    //   this.role = decoded.role;
    // } catch (e) {
    //   return false;
    // }
    // true or false
    console.log(token);
    return !!token;
    // return !this.jwtHelper.isTokenExpired(token || "");
  }

  public getRole() {
    return this.role;
  }
}
