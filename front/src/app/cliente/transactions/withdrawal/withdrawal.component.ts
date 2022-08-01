import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-withdrawal',
  templateUrl: './withdrawal.component.html',
  styleUrls: ['./withdrawal.component.css']
})
export class WithdrawalComponent implements OnInit {
  
  constructor() { }

  ngOnInit(): void {
  }

  sacar(value: string): void {
    let parsed = Number(value);
    console.log(`Sacando ${parsed}`);

    // TODO: verificar se a quantia existe
    // TODO: sacar a quantia
  }
}
