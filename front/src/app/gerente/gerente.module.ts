import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultaClientesComponent } from './consulta-clientes/consulta-clientes.component';
import { ConsultaClienteComponent } from './consulta-cliente/consulta-cliente.component';
import { SolicitacoesComponent } from './solicitacoes/solicitacoes.component';



@NgModule({
  declarations: [
    ConsultaClientesComponent,
    ConsultaClienteComponent,
    SolicitacoesComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ConsultaClienteComponent,
    ConsultaClientesComponent,
    SolicitacoesComponent
  ]
})
export class GerenteModule { }
