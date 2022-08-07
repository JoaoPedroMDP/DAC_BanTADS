import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RegisterComponent } from "./register/register.component";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { ClientComponent } from "./client/client.component";
import { MaterialModule } from "../material";
import { IMaskModule } from "angular-imask";
import { AppRoutingModule } from "../app-routing.module";
import { HomeComponent } from "./home/home.component";
import { DepositComponent } from "./transactions/deposit/deposit.component";
import { WithdrawalComponent } from "./transactions/withdrawal/withdrawal.component";
import { TransferComponent } from "./transactions/transfer/transfer.component";
import { StatementComponent } from "./transactions/statement/statement.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgxCurrencyModule } from "ngx-currency";

@NgModule({
  declarations: [
    ClientComponent,
    RegisterComponent,
    HomeComponent,
    DepositComponent,
    WithdrawalComponent,
    TransferComponent,
    StatementComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FeatherModule.pick(allIcons),
    MaterialModule,
    IMaskModule,
    ReactiveFormsModule,
    FormsModule,
    NgxCurrencyModule,
    ReactiveFormsModule
  ],
})
export class ClienteModule {}
