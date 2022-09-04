import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "../material";
import { LoginComponent } from "./login/login.component";
import { AppRoutingModule } from "../app-routing.module";
import { FormsModule } from "@angular/forms";
import { AuthService } from "./services/auth.service";

@NgModule({
  declarations: [LoginComponent],
  imports: [CommonModule, MaterialModule, AppRoutingModule, FormsModule],
})
export class AuthModule {}
