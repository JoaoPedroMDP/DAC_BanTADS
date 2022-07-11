import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AlertsComponent } from "./components/alerts/alerts.component";
import { ButtonsComponent } from "./components/buttons/buttons.component";
import { ChipsComponent } from "./components/chips/chips.component";
import { ExpansionComponent } from "./components/expansion/expansion.component";
import { FormsComponent } from "./components/forms/forms.component";
import { GridListComponent } from "./components/grid-list/grid-list.component";
import { MenuComponent } from "./components/menu/menu.component";
import { ProgressSnipperComponent } from "./components/progress-snipper/progress-snipper.component";
import { ProgressComponent } from "./components/progress/progress.component";
import { SlideToggleComponent } from "./components/slide-toggle/slide-toggle.component";
import { SliderComponent } from "./components/slider/slider.component";
import { SnackbarComponent } from "./components/snackbar/snackbar.component";
import { TabsComponent } from "./components/tabs/tabs.component";
import { ToolbarComponent } from "./components/toolbar/toolbar.component";
import { TooltipsComponent } from "./components/tooltips/tooltips.component";
import { ProductComponent } from "./dashboard/dashboard-components/product/product.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { ConsultaClienteComponent } from "./gerente/consulta-cliente/consulta-cliente.component";
import { ConsultaClientesComponent } from "./gerente/consulta-clientes/consulta-clientes.component";
import { SolicitacoesComponent } from "./gerente/solicitacoes/solicitacoes.component";
import { ClientComponent } from "./layouts/client/client.component";
import { FullComponent } from "./layouts/full/full.component";
import { LoginComponent } from "./layouts/login/login.component";
import { RegisterComponent } from "./layouts/register/register.component";
import { DepositComponent } from "./transactions/deposit/deposit.component";
import { StatementComponent } from "./transactions/statement/statement.component";
import { TransferComponent } from "./transactions/transfer/transfer.component";
import { WithdrawalComponent } from "./transactions/withdrawal/withdrawal.component";

const routes: Routes = [
  {
    path: "",
    component: FullComponent,
    children: [
      { path: "", redirectTo: "/home", pathMatch: "full" },
      { path: "home", component: DashboardComponent },
      { path: "alerts", component: AlertsComponent },
      { path: "forms", component: FormsComponent },
      { path: "table", component: ProductComponent },
      { path: "grid-list", component: GridListComponent },
      { path: "menu", component: MenuComponent },
      { path: "tabs", component: TabsComponent },
      { path: "expansion", component: ExpansionComponent },
      { path: "chips", component: ChipsComponent },
      { path: "progress", component: ProgressComponent },
      { path: "toolbar", component: ToolbarComponent },
      { path: "progress-snipper", component: ProgressSnipperComponent },
      { path: "snackbar", component: SnackbarComponent },
      { path: "slider", component: SliderComponent },
      { path: "slide-toggle", component: SlideToggleComponent },
      { path: "tooltip", component: TooltipsComponent },
      { path: "button", component: ButtonsComponent },
    ],
  },
  {
    path: "transacoes",
    component: FullComponent,
    children: [
      { path: "depositar", component: DepositComponent },
      { path: "sacar", component: WithdrawalComponent },
      { path: "transferir", component: TransferComponent },
      { path: "extrato", component: StatementComponent },
    ],
  },
  {
    path: "gerente",
    component: FullComponent,
    children: [
      { path: "solicitacoes", component: SolicitacoesComponent },
      { path: "consulta-clientes", component: ConsultaClientesComponent },
      { path: "consulta-cliente", component: ConsultaClienteComponent },
    ],
  },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
  { path: "client", component: ClientComponent },

  // transacoes/depositar
  // transacoes/sacar
  // transacoes/transferir
  { path: "", redirectTo: "/home", pathMatch: "full" },
  { path: "**", redirectTo: "/home", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
