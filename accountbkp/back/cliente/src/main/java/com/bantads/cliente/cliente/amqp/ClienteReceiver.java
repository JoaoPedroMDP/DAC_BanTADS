package com.bantads.cliente.cliente.amqp;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

import com.bantads.cliente.cliente.model.Cliente;
import com.bantads.cliente.cliente.repositories.ClienteRepository;
import com.bantads.cliente.cliente.serializers.ClienteDTO;

// import com.bantads.auth.auth.serializers.BasicClienteDTO;

@RabbitListener(queues = "cliente")
public class ClienteReceiver {

  @Autowired
  private ClienteRepository repo;

  @Autowired
  private ClienteProducer sender;

  @RabbitHandler
  public void receive(@Payload ClienteTransfer clienteTransfer) {
    System.out.println("Recebendo cliente " + clienteTransfer.getCliente());
    if (clienteTransfer.getAction().equals("get-cliente")) {
      Cliente cliente = repo.findById(clienteTransfer.getCliente().getId()).get();

      ModelMapper modelMapper = new ModelMapper();
      ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);

      clienteTransfer.setCliente(clienteDTO);
      clienteTransfer.setAction("return-cliente");

      sender.send(clienteTransfer);
    }
  }
}