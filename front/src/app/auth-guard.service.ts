import { Injectable } from "@angular/core";
import { Router, CanActivate, ActivatedRouteSnapshot } from "@angular/router";
import jwtDecode from "jwt-decode";
import { AuthService } from "src/app/auth/services/auth.service";

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(public auth: AuthService, public router: Router) {}
  canActivate(route: ActivatedRouteSnapshot): boolean {
    if (!this.auth.isAuthenticated()) {
      this.router.navigate(["login"]);
      return false;
    }

    const routeData: any = route.data;
    const expectedRole = routeData.expectedRole;
    const auth = localStorage.getItem("auth");

    console.log(expectedRole);
    if (!auth) return false;

    try {
      const token: string = JSON.parse(auth).token;
      const decodedToken: any = jwtDecode(token);

      if (decodedToken.role !== expectedRole) {
        this.router.navigate(["login"]);
        return false;
      }
    } catch (err) {
      console.log(err);
    }
    return true;
  }
}
