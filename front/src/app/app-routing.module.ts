import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./auth/login/login.component";
import { ClientComponent } from "./cliente/client/client.component";
import { RegisterComponent } from "./cliente/register/register.component";
import { AdminComponent } from "./layouts/admin/admin.component";
import { CriarGerenteComponent } from "./layouts/admin/gerente/criar-gerente/criar-gerente.component";
import { ConsultaClienteComponent } from "./gerente/consulta-cliente/consulta-cliente.component";
import { ConsultaClientesComponent } from "./gerente/consulta-clientes/consulta-clientes.component";
import { SolicitacoesComponent } from "./gerente/solicitacoes/solicitacoes.component";
import { GerenteComponent } from "./layouts/admin/gerente/gerente/gerente.component";
import { HomeComponent } from "./cliente/home/home.component";
import { DepositComponent } from "./cliente/transactions/deposit/deposit.component";
import { WithdrawalComponent } from "./cliente/transactions/withdrawal/withdrawal.component";
import { TransferComponent } from "./cliente/transactions/transfer/transfer.component";
import { StatementComponent } from "./cliente/transactions/statement/statement.component";

const routes: Routes = [
  {
    path: "gerente",
    // component: FullComponent,
    children: [
      { path: "solicitacoes", component: SolicitacoesComponent },
      { path: "consulta-clientes", component: ConsultaClientesComponent },
      { path: "consulta-cliente/:id", component: ConsultaClienteComponent },
    ],
  },
  { path: "admin", component: AdminComponent },
  { path: "admin/gerente", component: CriarGerenteComponent },
  { path: "admin/gerenteperfil", component: GerenteComponent },
  { path: "login", component: LoginComponent },
  {
    path: "cliente",
    component: ClientComponent,
    children: [
      { path: "", component: HomeComponent },
      { path: "register", component: RegisterComponent },
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
