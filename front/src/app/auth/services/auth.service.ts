import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private url = "http://localhost:8000/auth";
  private user = null;

  constructor(private http: HttpClient) {}

  public login(data: { email: string; password: string }) {
    return this.http.post(this.url + "/login", data).subscribe((user) => {
      console.log(user);
      return user;
    });
  }
}
