import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { FormsModule } from "@angular/forms";
import { MatTableModule } from "@angular/material/table";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FullComponent } from "./layouts/full/full.component";
import { DemoFlexyModule } from "./demo-flexy-module";
import { HttpClientModule } from "@angular/common/http";

// Modules
import { DashboardModule } from "./dashboard/dashboard.module";
import { ComponentsModule } from "./components/components.module";
import { LoginComponent } from "./layouts/login/login.component";
import { RegisterComponent } from "./layouts/register/register.component";
import { IMaskModule } from "angular-imask";
import { TransactionsModule } from "./transactions/transactions.module";

import { CriarGerenteComponent } from "./layouts/admin/gerente/criar-gerente/criar-gerente.component";
import { MatSortModule } from "@angular/material/sort";
import { AdminComponent } from "./layouts/admin/admin.component";
import { GerenteComponent } from './layouts/admin/gerente/gerente/gerente.component';

@NgModule({
  declarations: [
    AppComponent,
    FullComponent,
    LoginComponent,
    RegisterComponent,
    CriarGerenteComponent,
    AdminComponent,
    GerenteComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FeatherModule.pick(allIcons),
    DemoFlexyModule,
    DashboardModule,
    ComponentsModule,
    FormsModule,
    IMaskModule,
    TransactionsModule,
    HttpClientModule,
    MatTableModule,
    MatSortModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [MatTableModule],
})
export class AppModule {}
