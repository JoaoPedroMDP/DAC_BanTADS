import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ConsultaClientesComponent } from "./consulta-clientes/consulta-clientes.component";
import { ConsultaClienteComponent } from "./consulta-cliente/consulta-cliente.component";
import { SolicitacoesComponent } from "./solicitacoes/solicitacoes.component";
import { MaterialModule } from "../material";
import { RouterModule } from "@angular/router";
import { FullComponent } from "./full/full.component";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";

@NgModule({
  declarations: [
    ConsultaClientesComponent,
    ConsultaClienteComponent,
    SolicitacoesComponent,
    FullComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    FeatherModule.pick(allIcons),
  ],
  exports: [
    ConsultaClienteComponent,
    ConsultaClientesComponent,
    SolicitacoesComponent,
  ],
})
export class GerenteModule {}
