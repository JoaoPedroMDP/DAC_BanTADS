import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/auth/services/auth.service";
import { NotificationService } from "src/app/services/notification.service";
import { ClienteService } from "../../services/cliente.service";

@Component({
  selector: "app-transfer",
  templateUrl: "./transfer.component.html",
  styleUrls: ["./transfer.component.css"],
})
export class TransferComponent implements OnInit {
  transferValue!: number;
  transferDestiny!: string;
  balance!: number;

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private notifyService: NotificationService,
    private router: Router,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.transferValue = 0;
    this.transferDestiny = "";
    this.balance = 0;
  }

  async transfer(): Promise<void> {
    const account: any = this.clienteService.getAccount();
    let userId: number | undefined = this.auth.getAuth()?.user;
    if (userId === undefined) {
      console.log("Usuário não encontrado");
      this.notifyService.showError("", "Usuário não encontrado");
      this.router.navigate(["/login"]);
      return;
    }

    this.notifyService.showInfo(
      "",
      `Transferindo ${this.transferValue} para ${this.transferDestiny}...`
    );
    // Espera puxar do back end os recusos pra depois efetuar o resto da função
    await this.callApiBalance(userId);
    if (this.transferValue > account.balance) {
      this.notifyService.showError("Saldo insuficiente", "Erro");
    } else {
      await this.callApiTransfer(userId);
      this.notifyService.showSuccess("Transferência realizada.", "Sucesso");
    }
  }

  async callApiBalance(userId: number) {
    const account: any = this.clienteService.getAccount();
    // Puxa o saldo do back end e armazena na variavel da classe
    this.http
      .get<Record<string, any>>(
        "http://localhost:3000/accounts/" + account.id + "/balance"
      )
      .subscribe((response) => {
        this.balance = response["balance"];
      });
  }

  async callApiTransfer(userId: number) {
    const account: any = this.clienteService.getAccount();
    this.http
      .post<Record<string, any>>(
        "http://localhost:3000/accounts/" + account.id + "/transfer",
        { amount: this.transferValue, to: this.transferDestiny }
      )
      .subscribe((response) => {
        console.log(response);
      });
  }
}
