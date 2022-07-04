import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepositComponent } from './deposit/deposit.component';
import { DemoFlexyModule } from '../demo-flexy-module';
import { WithdrawalComponent } from './withdrawal/withdrawal.component';
import { TransferComponent } from './transfer/transfer.component';



@NgModule({
  declarations: [
    DepositComponent,
    WithdrawalComponent,
    TransferComponent
  ],
  imports: [
    CommonModule,
    DemoFlexyModule
  ]
})
export class TransactionsModule { }
