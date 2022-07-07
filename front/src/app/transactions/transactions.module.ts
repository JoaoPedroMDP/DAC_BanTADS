import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepositComponent } from './deposit/deposit.component';
import { DemoFlexyModule } from '../demo-flexy-module';
import { WithdrawalComponent } from './withdrawal/withdrawal.component';
import { TransferComponent } from './transfer/transfer.component';
import { StatementComponent } from './statement/statement.component';

@NgModule({
  declarations: [
    DepositComponent,
    WithdrawalComponent,
    TransferComponent,
    StatementComponent
  ],
  imports: [
    CommonModule,
    DemoFlexyModule
  ]
})
export class TransactionsModule { }
