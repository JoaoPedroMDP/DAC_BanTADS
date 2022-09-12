import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./auth/login/login.component";
import { ClientComponent } from "./cliente/client/client.component";
import { RegisterComponent } from "./cliente/register/register.component";
import { AdminComponent } from "./admin/listaDeGerentes/admin.component";
import { CriarGerenteComponent } from "./admin/criar-gerente/criar-gerente.component";
import { ConsultaClienteComponent } from "./gerente/consulta-cliente/consulta-cliente.component";
import { ConsultaClientesComponent } from "./gerente/consulta-clientes/consulta-clientes.component";
import { SolicitacoesComponent } from "./gerente/solicitacoes/solicitacoes.component";
import { GerenteComponent } from "./admin/perfilGerente/gerente.component";
import { HomeComponent } from "./cliente/home/home.component";
import { DepositComponent } from "./cliente/transactions/deposit/deposit.component";
import { WithdrawalComponent } from "./cliente/transactions/withdrawal/withdrawal.component";
import { TransferComponent } from "./cliente/transactions/transfer/transfer.component";
import { StatementComponent } from "./cliente/transactions/statement/statement.component";
import { AdminSideBarComponent } from "./admin/adminSideBar/admin.component";
import { FullComponent } from "./gerente/full/full.component";
import { ConsultarClienteComponent } from "./gerente/consultar-cliente/consultar-cliente.component";
import { AuthGuardService } from "./auth-guard.service";
const routes: Routes = [
  {
    path: "gerente",
    component: FullComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRole: "GERENTE",
    },
    children: [
      { path: "", component: ConsultaClientesComponent },
      { path: "consultar-cliente", component: ConsultarClienteComponent },
      { path: "solicitacoes", component: SolicitacoesComponent },
      { path: "consulta-clientes", component: ConsultaClientesComponent },
      { path: "consulta-cliente/:id", component: ConsultaClienteComponent },
    ],
  },
  {
    path: "admin",
    component: AdminSideBarComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRole: "ADMIN",
    },
    children: [
      { path: "", component: AdminComponent },
      { path: "gerente", component: CriarGerenteComponent },
      { path: "gerente/:id", component: CriarGerenteComponent },
      { path: "gerenteperfil/:id", component: GerenteComponent },
    ],
  },

  { path: "login", component: LoginComponent },
  { path: "cliente/register", component: RegisterComponent },
  {
    path: "cliente",
    component: ClientComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRole: "CLIENTE",
    },
    children: [
      { path: "", component: HomeComponent },
      {
        path: "transacoes",
        children: [
          { path: "", redirectTo: "cliente", pathMatch: "full" },
          { path: "depositar", component: DepositComponent },
          { path: "sacar", component: WithdrawalComponent },
          { path: "transferir", component: TransferComponent },
          { path: "extrato", component: StatementComponent },
        ],
      },
      { path: "**", redirectTo: "", pathMatch: "full" },
    ],
  },

  { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: "**", redirectTo: "/login", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
