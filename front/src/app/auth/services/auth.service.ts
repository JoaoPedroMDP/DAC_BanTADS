import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Auth } from "./types";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private url = "http://localhost:5000/auth";
  private auth: Auth | null = null;

  constructor(private http: HttpClient, private router: Router) {}

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
      .subscribe((response: any) => {
        //TODO: error handling
        if (response.success) {
          this.setAuth(response.data);
          this.navigateToUserHome();
          return response;
        }
      });
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
}
