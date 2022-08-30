import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";

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
      const extraData = transaction["extraData"] == "" ? undefined : transaction["extraData"];

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
  range = new FormGroup({
    start: new FormControl(null),
    end: new FormControl(null),
  });

  statement!: Statement;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    let startDate: string = new Date().getTime().toString();
    let endDate: string = new Date().getTime().toString(); // Um dia
    this.chamaApi(startDate, endDate);
  }

  async chamaApi(inicio: string, fim: string) {
    this.http
      .get<Record<string, any>>(
        "http://localhost:5003/accounts/13/statement?from=1661556686000&to=1661660000000"
      )
      .subscribe((response) => {
        // O '+' é pra converter para numerico
        const data = response["data"]
        const startDate = new Date(+data["startDate"]);
        const endDate = new Date(+data["endDate"]);
        const days = data["days"];

        this.statement = new Statement(startDate, endDate, days);
      });
  }
}
