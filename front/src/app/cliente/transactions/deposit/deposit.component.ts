import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {
  depositValue!: number;
  constructor() { }

  ngOnInit(): void {
    this.depositValue = 0;
  }

  deposit(): void {
    alert(`Depositando ${this.depositValue}`)
  }
}
