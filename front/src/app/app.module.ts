import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { MatSortModule } from "@angular/material/sort";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { IMaskModule } from "angular-imask";
import { MatTableModule } from "@angular/material/table";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { MaterialModule } from "./material";

import { AuthModule } from "./auth/auth.module";
import { ClienteModule } from "./cliente/cliente.module";
import { GerenteModule } from "./gerente";

import { AdminModule } from "./admin/admin.module";
import { ToastrModule } from "ngx-toastr";
// import { JwtModule } from "@auth0/angular-jwt";

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FeatherModule.pick(allIcons),
    MaterialModule,
    FormsModule,
    IMaskModule,
    HttpClientModule,
    MatTableModule,
    MatSortModule,
    GerenteModule,
    ClienteModule,
    AuthModule,
    AdminModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [MatTableModule],
})
export class AppModule {}
