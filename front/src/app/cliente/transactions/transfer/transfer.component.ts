import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {
  transferValue!: number;
  transferDestiny!: string;

  constructor() { }

  ngOnInit(): void {
    this.transferValue = 0;
    this.transferDestiny = '';
  }

  transfer(): void {
    alert(`Transferindo ${this.transferValue} para ${this.transferDestiny}`);
  }

}
