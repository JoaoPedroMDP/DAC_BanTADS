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

// Modules
import { DashboardModule } from "./dashboard/dashboard.module";
import { ComponentsModule } from "./components/components.module";
import { LoginComponent } from "./layouts/login/login.component";
import { RegisterComponent } from "./layouts/register/register.component";
import { IMaskModule } from "angular-imask";

@NgModule({
  declarations: [
    AppComponent,
    FullComponent,
    LoginComponent,
    RegisterComponent,
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
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
