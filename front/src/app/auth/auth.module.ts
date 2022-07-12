import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DemoFlexyModule } from "../demo-flexy-module";
import { LoginComponent } from "./login/login.component";
import { AppRoutingModule } from "../app-routing.module";

@NgModule({
  declarations: [LoginComponent],
  imports: [CommonModule, DemoFlexyModule, AppRoutingModule],
})
export class AuthModule {}
