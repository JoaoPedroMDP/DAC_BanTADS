import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-withdrawal',
  templateUrl: './withdrawal.component.html',
  styleUrls: ['./withdrawal.component.css']
})
export class WithdrawalComponent implements OnInit {
  withdrawalValue!: number;
  balance!: number;

  constructor(private http: HttpClient, private notifyService: NotificationService) {}

  ngOnInit(): void {
    this.withdrawalValue = 0;
    this.balance = 0;
  }

  async withdraw(): Promise<void> {
    this.notifyService.showInfo("", `Sacando ${this.withdrawalValue}...`);
    // Espera puxar do back end os recusos pra depois efetuar o resto da função
    await this.callApiBalance();
    if(this.withdrawalValue > this.balance){
      this.notifyService.showError("Saldo insuficiente", "Erro");
    }else{
      await this.callApiWithdrawal();
      this.notifyService.showSuccess("Saque realizado com sucesso", "Sucesso");
    }
  }

  async callApiBalance(){
    // Puxa o saldo do back end e armazena na variavel da classe
    this.http
      .get<Record<string, any>>(
        "https://joaopedromdp-dac-bantads-q99j6vgv9p52x94x-5003.githubpreview.dev/accounts/1/balance"
      )
      .subscribe((response) => {
        this.balance = response["balance"];
      });
  }

  async callApiWithdrawal() {
    this.http
      .post<Record<string, any>>(
        "https://joaopedromdp-dac-bantads-q99j6vgv9p52x94x-5003.githubpreview.dev/accounts/1/withdraw",
        { amount: this.withdrawalValue }
      )
      .subscribe((response) => {
        console.log(response);
      });
  }
}
