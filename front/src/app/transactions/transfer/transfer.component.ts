import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  transferir(value: string, destiny: string): void {
    let parsed = Number(value);
    console.log(`Transferindo ${parsed} para ${destiny}`);
  }

}
