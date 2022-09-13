import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthService } from "src/app/auth/services/auth.service";
import { NotificationService } from "src/app/services/notification.service";
import { ClienteService } from "../../services/cliente.service";

const transactionTypes: Record<string, any> = {
  out: {
    red: 255,
    green: 80,
    blue: 80,
    opacity: 0.3,
  },
  in: {
    red: 80,
    green: 80,
    blue: 255,
    opacity: 0.3,
  },
};

class Transaction {
  public datetime!: Date;
  public description!: string;
  public type!: string;
  public typeColor!: Record<string, any>;
  public value!: number;
  public transferDetails!: Record<string, any>;
  public typeName!: string;

  constructor(
    datetime: Date,
    description: string,
    type: string,
    value: number,
    transferDetails: Record<string, any> | undefined = undefined
  ) {
    this.datetime = datetime;
    this.description = description;
    this.type = type;
    this.value = value;

    if (transferDetails !== undefined) {
      this.typeColor = this.determineTransactionColor(type, transferDetails);
      this.transferDetails = this.processTransferDetails(transferDetails);
    } else {
      this.typeColor = this.determineTransactionColor(type);
    }
    this.typeName = this.determineTransactionTypeName();
  }

  public getTypeColor(solid: boolean) {
    return (
      "rgb(" +
      this.typeColor["red"] +
      ", " +
      this.typeColor["green"] +
      ", " +
      this.typeColor["blue"] +
      ", " +
      (solid ? 1 : this.typeColor["opacity"]) +
      ")"
    );
  }

  private determineTransactionColor(
    type: string,
    transferDetails: Record<string, any> | null = null
  ): Record<string, any> {
    if (
      type === "deposit" ||
      (type === "transfer" &&
        transferDetails !== null &&
        transferDetails["origin"] !== undefined)
    ) {
      return transactionTypes["in"];
    } else {
      return transactionTypes["out"];
    }
  }

  private determineTransactionTypeName(): string {
    if (this.type === "deposit") {
      return "Depósito";
    } else if (this.type === "transfer") {
      return "Transferência";
    } else {
      return "Saque";
    }
  }

  private processTransferDetails(
    transferDetails: Record<string, any>
  ): Record<string, any> {
    const moneyWasSent = transferDetails["destiny"] !== undefined;
    console.log(moneyWasSent);
    return {
      type: moneyWasSent ? "outcome" : "income",
      agent: moneyWasSent
        ? transferDetails["destiny"]
        : transferDetails["origin"],
    };
  }
}

class Day {
  public consolidatedValue!: number;
  public date!: Date;
  public transactions!: Array<Transaction>;

  public constructor(
    consolidatedValue: number,
    date: Date,
    transactions: Array<Record<string, any>>
  ) {
    this.consolidatedValue = consolidatedValue;
    this.date = date;
    this.transactions = this.assembleTransactions(transactions);
  }

  public assembleTransactions(
    transactions: Array<Record<string, any>>
  ): Array<Transaction> {
    let assembledTransactions = new Array<Transaction>();
    transactions.forEach((transaction) => {
      const extraData =
        transaction["extraData"] == "" ? undefined : transaction["extraData"];

      let assembledTransaction = new Transaction(
        new Date(+transaction["timestamp"]),
        transaction["description"],
        transaction["type"],
        transaction["amount"],
        extraData
      );
      assembledTransactions.push(assembledTransaction);
    });

    return assembledTransactions;
  }
}

class Statement {
  public startDate!: Date;
  public endDate!: Date;
  public days!: Array<Day>;

  constructor(
    startDate: Date,
    endDate: Date,
    days: Array<Record<string, any>>
  ) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.days = this.assembleDays(days);
  }

  public assembleDays(days: Array<Record<string, any>>): Array<Day> {
    let assembledDays: Array<Day> = [];

    days.forEach((day) => {
      const consolidatedValue = day["consolidatedValue"];
      const date = new Date(+day["date"]);
      const transactions = day["transactions"];

      let newDay = new Day(consolidatedValue, date, transactions);
      assembledDays.push(newDay);
    });

    return assembledDays;
  }
}

@Component({
  selector: "app-statement",
  templateUrl: "./statement.component.html",
  styleUrls: ["./statement.component.css"],
})
export class StatementComponent implements OnInit {
  datepicker = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  statement!: Statement;

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private notifyService: NotificationService,
    private router: Router,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {}

  dateChangeHandler() {
    let startDate: string = new Date(this.datepicker.value.start)
      .valueOf()
      .toString();
    let endDate: string = new Date(this.datepicker.value.end)
      .valueOf()
      .toString();
    this.callApiStatement(startDate, endDate);
  }

  async callApiStatement(inicio: string, fim: string) {
    let userId: number | undefined = this.auth.getAuth()?.user;
    if (userId === undefined) {
      console.log("Usuário não encontrado");
      this.notifyService.showError("", "Usuário não encontrado");
      this.router.navigate(["/login"]);
      return;
    }

    const account: any = this.clienteService.getAccount();

    this.http
      .get<Record<string, any>>(
        "http://localhost:3000/accounts/" +
          account.id +
          "/statement?from=" +
          inicio +
          "&to=" +
          fim
      )
      .subscribe((response) => {
        // O '+' é pra converter para numerico
        const data = response["data"];
        const startDate = new Date(+data["startDate"]);
        const endDate = new Date(+data["endDate"]);
        const days = data["days"];

        this.statement = new Statement(startDate, endDate, days);
      });
  }
}
