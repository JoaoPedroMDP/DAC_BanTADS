import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

const transactionTypes: Record<string, any> = {
  'out': "rgb(255, 62, 70, 0.5)",
  'in': 'rgb(51, 51, 255   , 0.5)'
};

class Transaction{
  public datetime!: Date;
  public description!: string;
  public typeColor!: string;
  public value!: number;
  public transferDetails!: Record<string, any>;
}

class TransactionResponse{
  public datetime!: string;
  public description!: string;
  public type!: string;
  public value!: number;
  public transferDetails!: Record<string, any>;
}

class TransactionAssembler{
  public static assemble(transaction: Array<TransactionResponse>): Array<Transaction>{
    let assembledTransactions = new Array<Transaction>();

    transaction.forEach(transaction => {
      let assembledTransaction = new Transaction();
      assembledTransaction.datetime = new Date(+transaction.datetime);
      assembledTransaction.description = transaction.description;
      assembledTransaction.typeColor = TransactionAssembler.determineTransactionColor(transaction);
      assembledTransaction.value = transaction.value;
      assembledTransaction.transferDetails = transaction.transferDetails;
      assembledTransactions.push(assembledTransaction);
    });

    return assembledTransactions;
  }

  public static determineTransactionColor(transaction: TransactionResponse): string{
    if(transaction.type === 'deposit' || transaction.type === 'transfer' && transaction.transferDetails['origin'] !== undefined){
      return transactionTypes['in'];
    }else{
      return transactionTypes['out'];
    }
  }
}

class StatementDay{
  public consolidatedValue!: number;
  public date!: Date;
  public transactions!: Array<Transaction>;
}

class StatementDayResponse{
  public consolidatedValue!: number;
  public date!: string;
  public transactions!: Array<TransactionResponse>;
}

class DayAssembler{
  public static assemble(days: Array<StatementDayResponse>): Array<StatementDay>{
    let assembledDays: Array<StatementDay> = [];

    days.forEach(day => {
      let newDay = new StatementDay();
      newDay.consolidatedValue = day.consolidatedValue;
      newDay.date = new Date(+day.date);
      newDay.transactions = TransactionAssembler.assemble(day.transactions);
      assembledDays.push(newDay);
    });

    return assembledDays;
  }
}

class Statement{
  public startDate!: Date;
  public endDate!: Date;
  public days!: Array<StatementDay>;

  constructor(startDate: Date, endDate: Date, days: Array<StatementDayResponse>){
    this.startDate = startDate;
    this.endDate = endDate;
    console.log(days);
    this.days = DayAssembler.assemble(days);
  }

}

class StatementResponse {
  public startDate!: number;
  public endDate!: number;
  public days!: Array<StatementDayResponse>;
}

@Component({
  selector: 'app-statement',
  templateUrl: './statement.component.html',
  styleUrls: ['./statement.component.css']
})
export class StatementComponent implements OnInit {
  range = new FormGroup({
    start: new FormControl(null),
    end: new FormControl(null),
  });
  
  statement!: Statement;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    let startDate: string = new Date().getTime().toString();
    let endDate: string = new Date().getTime().toString(); // Um dia
    this.chamaApi(startDate, endDate); 
  }

  async chamaApi(inicio: string, fim: string){
    console.log(inicio, fim)
    this.http.get<StatementResponse>("https://run.mocky.io/v3/5337645b-55bb-4221-92cc-ac052f3bd689")
      .subscribe(response => {
        // O '+' é pra converter para numerico
        let startDate = new Date(+response.startDate);
        let endDate = new Date(+response.endDate);
        this.statement = new Statement(startDate, endDate, response.days);
      }
    )
  }
}
