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
import { ConsultarClienteComponent } from "./consultar-cliente/consultar-cliente.component";
import { IMaskModule } from "angular-imask";
import { FormsModule } from "@angular/forms";
import { GerenteService } from "./gerente.service";

@NgModule({
  declarations: [
    ConsultaClientesComponent,
    ConsultaClienteComponent,
    SolicitacoesComponent,
    FullComponent,
    ConsultarClienteComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    FormsModule,
    FeatherModule.pick(allIcons),
    IMaskModule,
  ],
  exports: [
    ConsultaClienteComponent,
    ConsultaClientesComponent,
    SolicitacoesComponent,
  ],
})
export class GerenteModule {}
