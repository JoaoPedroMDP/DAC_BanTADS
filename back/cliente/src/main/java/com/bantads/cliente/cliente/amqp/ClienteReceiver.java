package com.bantads.cliente.cliente.amqp;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

import com.bantads.cliente.cliente.model.Cliente;
import com.bantads.cliente.cliente.model.Endereco;
import com.bantads.cliente.cliente.repositories.ClienteRepository;
import com.bantads.cliente.cliente.repositories.EnderecoRepository;
import com.bantads.cliente.cliente.serializers.AccountStatus;
import com.bantads.cliente.cliente.serializers.ClienteDTO;
import com.bantads.cliente.cliente.serializers.EnderecoDTO;
import com.bantads.cliente.cliente.utils.ValidarCpf;

// import com.bantads.auth.auth.serializers.BasicClienteDTO;

@RabbitListener(queues = "cliente")
public class ClienteReceiver {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private ClienteRepository repo;

  @Autowired
  private EnderecoRepository repoEnd;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private ClienteProducer sender;

  @Autowired
  private GerenteProducer gerenteSender;

  @RabbitHandler
  public ClienteTransfer receive(@Payload ClienteTransfer clienteTransfer) {
    System.out.println("Recebendo cliente " + clienteTransfer.getCliente());
    if (clienteTransfer.getAction().equals("get-cliente")) {
      Cliente cliente = repo.findById(clienteTransfer.getCliente().getId()).get();

      ModelMapper modelMapper = new ModelMapper();
      ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);

      clienteTransfer.setCliente(clienteDTO);
      clienteTransfer.setAction("return-cliente");

      sender.send(clienteTransfer);
      return clienteTransfer;
    } else if (clienteTransfer.getAction().equals("cliente-register")) {
      // luli é o meu amor do coração

      ClienteDTO cliente = clienteTransfer.getCliente();

      if (cliente.getNome() == null || cliente.getCpf() == null || cliente.getEndereco() == null) {
        clienteTransfer.setError("Dados do cliente inválidos");

        clienteTransfer.setAction("cliente-failed");

        this.template.convertAndSend("saga", clienteTransfer);
        // return new ResponseEntity<>("Dados do cliente i;nválidos",
        // HttpStatus.BAD_REQUEST);
      }

      if (cliente.getPassword() == null || cliente.getEmail() == null) {
        clienteTransfer.setError("Por favor, insira email e senha para completar seu cadastro!");
        clienteTransfer.setAction("cliente-failed");

        // this.template.convertAndSend("saga", clienteTransfer);
        return clienteTransfer;
      }

      if (!ValidarCpf.isCpfValid(cliente.getCpf())) {
        clienteTransfer.setError("CPF Inálido!");
        clienteTransfer.setAction("cliente-failed");

        // this.template.convertAndSend("saga", clienteTransfer);
        return clienteTransfer;
      }

      EnderecoDTO endereco = cliente.getEndereco();

      if (endereco.getRua() == null || endereco.getNumero() == null
          || endereco.getCidade() == null || endereco.getEstado() == null || endereco.getCep() == null) {

        clienteTransfer.setError("Dados de endereço inválido!");
        clienteTransfer.setAction("cliente-failed");

        this.template.convertAndSend("saga", clienteTransfer);
        return clienteTransfer;
      }

      try {
        Endereco end = repoEnd.save(mapper.map(cliente.getEndereco(), Endereco.class));

        cliente.setEndereco(mapper.map(end, EnderecoDTO.class));
        cliente.setAprovado(AccountStatus.ANALISE);

        // TODO: assinalar ao gerente com menos clientes
        // cliente.setGerente(1L);

        Cliente clienteObj = repo.save(mapper.map(cliente, Cliente.class));

        cliente.setId(clienteObj.getId());

        // Rabbit para criar a autenticação do usuário
        // sender.send(cliente);

        GerenteTransfer gt = new GerenteTransfer();

        gt.setAction("create-cliente");
        gt.setCliente(cliente.getId());

        gerenteSender.send(gt);
        cliente.setPassword("");

      } catch (Exception e) {

        clienteTransfer.setError("Erro interno ao criar cliente!");
        clienteTransfer.setAction("cliente-failed");

        this.template.convertAndSend("saga", clienteTransfer);
        return clienteTransfer;
      }

      clienteTransfer.setAction("cliente-ok");

      return clienteTransfer;

    } else if (clienteTransfer.getAction().equals("cliente-delete")) {
      ClienteDTO cliente = clienteTransfer.getCliente();

      Cliente clienteObj = repo.findById(cliente.getId()).get();

      if (clienteObj != null) {
        repo.delete(clienteObj);

        clienteTransfer.setAction("cliente-deleted");
        // this.template.convertAndSend("saga", clienteTransfer);
        return clienteTransfer;
      }

    } else if (clienteTransfer.getAction().equals("cliente-aprovado")) {
      ClienteDTO cliente = clienteTransfer.getCliente();

      Cliente clienteObj = repo.findById(cliente.getId()).get();

      cliente = mapper.map(clienteObj, ClienteDTO.class);

      if (clienteObj != null) {
        try {

          clienteObj.setAprovado(AccountStatus.APROVADO.name());
          repo.save(clienteObj);

          clienteTransfer.setAction("cliente-approved-ok");
          clienteTransfer.setCliente(cliente);
          // this.template.convertAndSend("saga", clienteTransfer);
          return clienteTransfer;
        } catch (Exception e) {

          System.out.println(e.getLocalizedMessage());
          clienteTransfer.setAction("cliente-approved-failed");

          return clienteTransfer;
        }
      }
      System.out.println("Cliente não encontrado");

      return clienteTransfer;

    } else if (clienteTransfer.getAction().equals("cliente-approved-rollback")) {
      ClienteDTO cliente = clienteTransfer.getCliente();

      Cliente clienteObj = repo.findById(cliente.getId()).get();

      if (clienteObj != null) {
        try {

          clienteObj.setAprovado(AccountStatus.ANALISE.name());
          repo.save(clienteObj);

          clienteTransfer.setAction("cliente-approved-rollback-ok");
          // this.template.convertAndSend("saga", clienteTransfer);
          return clienteTransfer;
        } catch (Exception e) {

          System.out.println(e.getLocalizedMessage());
          clienteTransfer.setAction("cliente-approved-rollback-failed");

          return clienteTransfer;
        }
      }
      System.out.println("Cliente não encontrado");

      return clienteTransfer;
    }

    System.out.println("Ação recebida não reconhecida: " + clienteTransfer.getAction());

    return null;
  }
}