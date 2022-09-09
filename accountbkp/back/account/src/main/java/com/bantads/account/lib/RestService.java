package com.bantads.account.lib;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    // public CustomerDTO getCustomer(String id) throws CustomerNotFound {
    // String url = "https://cliente:8080/clientes/" + id;
    // System.out.println("ASDASDASDASD");
    // try {
    // // return this.restTemplate.getForObject(url, CustomerDTO.class);
    // ResponseEntity<CustomerDTO> res = this.restTemplate.getForEntity(url,
    // CustomerDTO.class);
    // System.out.println(res.getStatusCode());
    // System.out.println(res.toString());
    // return res.getBody();
    // } catch (RestClientException e) {
    // throw new CustomerNotFound();
    // } catch (Exception e) {
    // System.out.println(e);
    // throw new CustomerNotFound();
    // }
    // }

    public CustomerDTO getCustomer(String id) {
        String url = "http://cliente:8080/clientes/" + id;

        try {
            // String resp = this.restTemplate.getForObject(url, String.class);
            // System.out.println(resp);
            ResponseEntity<JsonResponse> res = this.restTemplate.getForEntity(url, JsonResponse.class);
            ModelMapper mapper = new ModelMapper();
            return mapper.map(res.getBody().getData(), CustomerDTO.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            CustomerDTO defaultCustomer = new CustomerDTO();
            defaultCustomer.setNome("unknown");
            System.out.println("Não foi possível retornar o cliente de id " + id);
            return defaultCustomer;
        }
    }
}