import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultaClientesComponent } from './consulta-clientes/consulta-clientes.component';
import { ConsultaClienteComponent } from './consulta-cliente/consulta-cliente.component';
import { SolicitacoesComponent } from './solicitacoes/solicitacoes.component';
import { DemoFlexyModule } from '../demo-flexy-module';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    ConsultaClientesComponent,
    ConsultaClienteComponent,
    SolicitacoesComponent
  ],
  imports: [
    CommonModule,
    DemoFlexyModule,
    RouterModule
  ],
  exports: [
    ConsultaClienteComponent,
    ConsultaClientesComponent,
    SolicitacoesComponent
  ]
})
export class GerenteModule { }
