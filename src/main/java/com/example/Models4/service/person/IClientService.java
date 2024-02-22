package com.example.Models4.service.person;

import com.example.Models4.model.Client;

import java.util.List;

public interface IClientService {
    public List<Client> getClients();

    public void saveClient(Client client);

    public void deleteClient(Long id);

    public Client findClient(Long id);

    public List<Client> findByEmail(String email);

}