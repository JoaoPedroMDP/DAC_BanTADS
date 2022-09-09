package com.bantads.gerente.amqp;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.Payload;

import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.model.GerenteDTO;
import com.bantads.gerente.repository.GerenteRepository;

// import com.bantads.auth.auth.serializers.BasicClienteDTO;

@RabbitListener(queues = "gerente")
public class GerenteReceiver {

  @Autowired
  private GerenteRepository repo;

  @Autowired
  GerenteProducer sender;

  @RabbitHandler
  public void receive(@Payload GerenteTransfer gerenteTransfer) {
    if (gerenteTransfer.getAction().equals("create-cliente")) {
      System.out.println("Recebendo cliente");
      Gerente gerente = repo.findFirstByOrderByNumClientes();

      System.out.println("Gerente " + gerente.getId());
      gerente.setNumClientes(gerente.getNumClientes() + 1);

      repo.save(gerente);

      ModelMapper modelMapper = new ModelMapper();
      // GerenteDTO gerenteDTO = modelMapper.map(gerente, GerenteDTO.class);

      gerenteTransfer.setAction("set-gerente");
      gerenteTransfer.setGerente(gerente.getId().intValue());
      sender.send(gerenteTransfer);
    }
  }
}