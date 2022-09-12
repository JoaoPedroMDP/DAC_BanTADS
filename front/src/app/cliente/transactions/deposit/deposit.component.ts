import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {
  depositValue!: number;
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.depositValue = 0;
  }

  deposit(): void {
    alert(`Depositando ${this.depositValue}`)
    this.callApiDeposit();
  }

  async callApiDeposit() {
    this.http
      .post<Record<string, any>>(
        "https://joaopedromdp-dac-bantads-q99j6vgv9p52x94x-5003.githubpreview.dev/accounts/1/deposit",
        { amount: this.depositValue }
      )
      .subscribe((response) => {
        console.log(response);
      });
  }
}
