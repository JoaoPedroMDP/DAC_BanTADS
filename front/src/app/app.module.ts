import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { FormsModule } from "@angular/forms";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FullComponent } from "./layouts/full/full.component";
import { DemoFlexyModule } from "./demo-flexy-module";
import { HttpClientModule } from "@angular/common/http";

// Modules
import { DashboardModule } from "./dashboard/dashboard.module";
import { ComponentsModule } from "./components/components.module";
import { IMaskModule } from "angular-imask";
import { TransactionsModule } from "./transactions/transactions.module";
import { GerenteModule } from "./gerente";
import { ClienteModule } from "./cliente/cliente.module";
import { AuthModule } from "./auth/auth.module";

@NgModule({
  declarations: [AppComponent, FullComponent],
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
    GerenteModule,
    ClienteModule,
    AuthModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
