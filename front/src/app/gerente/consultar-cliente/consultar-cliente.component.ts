import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-consultar-cliente',
  templateUrl: './consultar-cliente.component.html',
  styleUrls: ['./consultar-cliente.component.css']
})
export class ConsultarClienteComponent implements OnInit {
  @ViewChild ('formCpf') formCpf! : NgForm;
  cpfMask = { mask: "000.000.000-00" };
  cpfInput : any;
  constructor() { }

  ngOnInit(): void {
  }
  onClickSubmit(data : any) {
    if(data.cpf.length > 10){
      // Aqui ele faz a pesquisa do cpf para id  
      window.location.replace("/gerente/consulta-cliente/1")
    }else{
      alert("Digite o cpf por completo");
    }
    
 }
}