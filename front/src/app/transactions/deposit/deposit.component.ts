import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
  }

  depositar(value: string): void {
    let parsed = Number(value);
    console.log(`Depositando ${parsed}`);
  }
}
