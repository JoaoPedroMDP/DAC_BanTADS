import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {
  transferValue!: number;
  transferDestiny!: string;
  balance!: number;

  constructor(private http: HttpClient, private notifyService: NotificationService) {}

  ngOnInit(): void {
    this.transferValue = 0;
    this.transferDestiny = '';
    this.balance = 0;
  }

  async transfer(): Promise<void> {
    this.notifyService.showInfo("", `Transferindo ${this.transferValue} para ${this.transferDestiny}...`);
    // Espera puxar do back end os recusos pra depois efetuar o resto da função
    await this.callApiBalance();
    if(this.transferValue > this.balance){
      this.notifyService.showError("Saldo insuficiente", "Erro");
    }else{
      await this.callApiTransfer();
      this.notifyService.showSuccess("Transferência realizada com sucesso", "Sucesso");
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

  async callApiTransfer() {
    this.http
      .post<Record<string, any>>(
        "https://joaopedromdp-dac-bantads-q99j6vgv9p52x94x-5003.githubpreview.dev/accounts/1/transfer",
        { amount: this.transferValue, to: this.transferDestiny }
      )
      .subscribe((response) => {
        console.log(response);
      });
  }
}
