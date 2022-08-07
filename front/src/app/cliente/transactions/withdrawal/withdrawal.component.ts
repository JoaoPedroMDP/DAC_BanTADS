import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-withdrawal',
  templateUrl: './withdrawal.component.html',
  styleUrls: ['./withdrawal.component.css']
})
export class WithdrawalComponent implements OnInit {
  withdrawalValue!: number;

  constructor() { }

  ngOnInit(): void {
    this.withdrawalValue = 0;
  }

  withdraw(): void {
    alert(`Sacando ${this.withdrawalValue}`);

    // TODO: verificar se a quantia existe
    // TODO: sacar a quantia
  }
}
