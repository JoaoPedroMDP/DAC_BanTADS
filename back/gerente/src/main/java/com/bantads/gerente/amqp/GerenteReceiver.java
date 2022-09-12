package com.bantads.gerente.amqp;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.Payload;

import com.bantads.gerente.utils.ValidarCpf;
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

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private ModelMapper mapper;

  @RabbitHandler
  public GerenteTransfer receive(@Payload GerenteTransfer gerenteTransfer) {
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
      return gerenteTransfer;
    } else if (gerenteTransfer.getAction().equals("create-gerente")) {
      System.out.println("Criando um Gerente");
      long i = gerenteTransfer.getGerente();
      Gerente gerenteTemporario = repo.findById(i).get();
      GerenteDTO gerenteD = new GerenteDTO();
      if (gerenteTemporario.getNome() != null) {
        gerenteD.setNome(gerenteTemporario.getNome());
      } else {
        gerenteTransfer.setError("Nome do Gerente inválido");

        gerenteTransfer.setAction("gerente-failed");

        this.template.convertAndSend("saga", gerenteTransfer);

        return gerenteTransfer;
      }
      if (gerenteTemporario.getEmail() != null) {
        gerenteD.setEmail(gerenteTemporario.getEmail());
      } else {
        gerenteTransfer.setError("Email do Gerente inválido");

        gerenteTransfer.setAction("gerente-failed");

        this.template.convertAndSend("saga", gerenteTransfer);

        return gerenteTransfer;
      }
      if ((gerenteTemporario.getCpf() != null) || (!ValidarCpf.isCpfValid(gerenteTemporario.getCpf()))) {
        gerenteD.setCpf(gerenteTemporario.getCpf());
      } else {
        gerenteTransfer.setError("Cpf do Gerente inválido");

        gerenteTransfer.setAction("gerente-failed");

        this.template.convertAndSend("saga", gerenteTransfer);
        return gerenteTransfer;
      }
      try {
        Gerente gerenteObj = repo.save(mapper.map(gerenteD, Gerente.class));
        gerenteD.setId(Math.toIntExact((gerenteObj.getId())));
      } catch (Exception e) {
        gerenteTransfer.setError("Erro interno ao criar gerente!");
        gerenteTransfer.setAction("gerente-failed");
        this.template.convertAndSend("saga", gerenteTransfer);
      }
      gerenteTransfer.setAction("gerente-ok");
      return gerenteTransfer;

    } else if (gerenteTransfer.getAction().equals("remove-gerente")) {
      System.out.println("Removendo Gerente");

      long i = gerenteTransfer.getGerente();
      Gerente gerente = repo.findById(i).get();
      if (gerente != null) {
        repo.delete(gerente);

        gerenteTransfer.setAction("gerente-deleted");
        // this.template.convertAndSend("saga", gerenteTransfer);
        return gerenteTransfer;
      }
    } else if (gerenteTransfer.getAction().equals("deny-cliente")) {
      System.out.println("Reprovando cliente");
      long i = gerenteTransfer.getGerente();
      Gerente g = repo.findById(i).get();
      GerenteDTO gerenteD = new GerenteDTO(Math.toIntExact(g.getId()), g.getNome(), g.getEmail(), g.getCpf());
      return gerenteTransfer;
    }
    System.out.println("Ação recebida não reconhecida: " + gerenteTransfer.getAction());

    return null;
  }
}