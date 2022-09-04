import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { AppRoutingModule } from "../app-routing.module";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { MaterialModule } from "../material";
import { IMaskModule } from "angular-imask";
import { GerenteComponent } from "./perfilGerente/gerente.component";
import { AdminComponent } from "./listaDeGerentes/admin.component";
import { CriarGerenteComponent } from "./criar-gerente/criar-gerente.component";
import { AdminSideBarComponent } from "./adminSideBar/admin.component";
import { FormsModule } from "@angular/forms";

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
    MaterialModule,
    IMaskModule,
    FormsModule,
  ],
})
export class AdminModule {}
