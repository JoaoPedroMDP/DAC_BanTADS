import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RegisterComponent } from "./register/register.component";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { ClientComponent } from "./client/client.component";
import { DemoFlexyModule } from "../demo-flexy-module";
import { IMaskModule } from "angular-imask";
import { AppRoutingModule } from "../app-routing.module";

@NgModule({
  declarations: [ClientComponent, RegisterComponent],
  imports: [
    CommonModule,
    AppRoutingModule,
    FeatherModule.pick(allIcons),
    DemoFlexyModule,
    IMaskModule,
  ],
})
export class ClienteModule {}
