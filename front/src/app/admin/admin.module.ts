import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { AppRoutingModule } from "../app-routing.module";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { DemoFlexyModule } from "../demo-flexy-module";
import { IMaskModule } from "angular-imask";
import { GerenteComponent } from "./perfilGerente/gerente.component";
import { AdminComponent } from "./listaDeGerentes/admin.component";
import { CriarGerenteComponent } from "./criar-gerente/criar-gerente.component";
import { AdminSideBarComponent } from "./adminSideBar/admin.component";

@NgModule({
  declarations: [
    GerenteComponent,
    AdminComponent,
    CriarGerenteComponent,
    AdminSideBarComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FeatherModule.pick(allIcons),
    DemoFlexyModule,
    IMaskModule,
  ],
})
export class AdminModule {}
